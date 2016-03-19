(ns learngo.editor
  (:require [cljs.pprint     :refer [pprint]]
            [learngo.board   :as bd]
            [learngo.problem :as pr]
            [learngo.rules   :as rules]
            [reagent.core    :as r]
            [reagent.ratom   :refer-macros [reaction]]))

(defn current-board [{:keys [stones size path]}]
  {:stones
   (dissoc
    (reduce (fn [board [color move]]
              (rules/play board color move))
            (merge stones {:size size})
            (map vector (cycle [:black :white]) path))
    :size
    :ko)})

(defn var-path [path]
  (->> path
       (interleave (repeat :vars))
       vec))

(defn get-in-var [state key]
  (let [path (-> state
                 :path
                 var-path
                 (conj key))]
    (get-in state path)))

(defn assoc-in-var [state & keyvals]
  (let [path (-> state
                 :path
                 var-path)]
    (if (empty? path)
      (apply assoc state keyvals)
      (apply update-in state path assoc keyvals))))

(defn assoc-in-var! [state & keyvals]
  (apply swap! state assoc-in-var keyvals))

(defn add-black-move [state pos]
  (let [path (-> state
                 :path
                 (conj pos)
                 var-path)]
    (-> state
        (assoc-in-var :status nil)
        (update :path conj pos)
        (update-in path merge {}))))

(defn set-white-reply [state pos]
  (if (= (get-in-var state :reply)
         pos)
    state
    (assoc-in-var state
                  :reply pos
                  :vars {})))

(defn play-handler [state board-state]
  (fn [pos]
    (swap! state update :history conj @board-state)
    (let [next-player (:player @board-state)]
      (if (= next-player :white)
        (swap! state add-black-move pos)
        (swap! state set-white-reply pos)))))

(defn root-board-state [state]
  (-> @state
      (select-keys [:marks :stones])
      (assoc :player :black)))

(defn up-handler [state board-state]
  (fn []
    (when (= (:player @board-state) :white)
      (swap! state update :path pop))
    (swap! state update :history pop)
    (reset! board-state (or (peek (:history @state))
                            (root-board-state state)))))

(defn top-handler [state board-state]
  (fn []
    (reset! board-state (root-board-state state))
    (swap! state assoc
           :path []
           :history [])))

(defn add-stone-handler [state board-state]
  (fn [pos]
    (swap! board-state update :player rules/other-color)
    (swap! state assoc-in [:stones pos] (:player @board-state))))

(def tool-handler
  {:play play-handler
   :black add-stone-handler
   :white add-stone-handler})

(defn after-play-handler [state board-state]
  (fn [pos]
    (let [handler (tool-handler (:tool @state))]
      ((handler state board-state) pos))))

(defn result-handler [state result]
  (fn []
    (assoc-in-var! state
                   :status result
                   :vars {})))

(defn any-handler [state board-state]
  (fn []
    (when (= (:player @board-state) :black)
      (swap! board-state update :player rules/other-color)
      (swap! state update :history conj @board-state)
      (swap! state add-black-move :any))))

(defn button [label handler]
  [:button {:type :button
            :on-click handler}
   label])

(defn select-tool-handler [state board-state tool]
  (fn []
    (swap! state assoc :tool tool)
    (if (#{:black :white} tool)
      (do
        ((top-handler state board-state))
        (swap! board-state assoc :player tool))
      (reset! board-state (root-board-state state)))))

(defn make [init-state]
  (let [state (r/atom (assoc init-state
                             :tool :black
                             :path []
                             :history []))
        board-state (r/atom (root-board-state state))
        after-play (after-play-handler state board-state)]
    (fn [board-settings]
      (let [resulting-problem (dissoc @state :path :history :status :tool)]
        [:div
         [:h3 "Editor"]
         [bd/board
          (merge board-settings {:width 400})
          board-state
          after-play]
         [button "Add Black" (select-tool-handler state board-state :black)]
         [button "Add White" (select-tool-handler state board-state :white)]
         [button "Play" (select-tool-handler state board-state :play)]
         [button "Up" (up-handler state board-state)]
         [button "Top" (top-handler state
                                    board-state)]
         [button "Win" (result-handler state :right)]
         [button "Lose" (result-handler state :wrong)]
         [button "Any Black" (any-handler state board-state)]
         [:h3 "Debug Info"]
         [:pre (with-out-str (pprint resulting-problem))]
         [:h3 "Problem Preview"]
         [(pr/problem
           resulting-problem
           identity)]]))))
