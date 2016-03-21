(ns learngo.editor
  (:require [cljs.pprint        :refer [pprint]]
            [learngo.board      :as bd]
            [learngo.forms      :as forms]
            [learngo.problem    :as pr]
            [learngo.rules      :as rules]
            [reagent.core       :as r]
            [reagent-forms.core :refer [bind-fields]]
            [reagent.ratom      :refer-macros [reaction]]))

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

(def editor-defaults
  {:width 400
   :tool :black
   :path []
   :history []
   :size 9
   :top 0
   :left 0
   :right 0
   :bottom 0})

(defn board-wrapper [init state after-play]
  (fn []
    [bd/board init state after-play]))

(defn make [init-state]
  (let [state (r/atom (merge editor-defaults init-state))
        board-state (r/atom (root-board-state state))
        after-play (after-play-handler state board-state)]
    (fn []
      (let [resulting-problem (dissoc @state :path :history :status :tool)]
        [:div
         [:h3 "Board Geometry"]
         [bind-fields (forms/geometry)
          state]
         [:div.row
          [:div.col-md-6
           [:h3 "Editor"]
           [(board-wrapper
             @state
             board-state
             after-play)]
           [button "Add Black" (select-tool-handler state board-state :black)]
           [button "Add White" (select-tool-handler state board-state :white)]
           [button "Play" (select-tool-handler state board-state :play)]
           [button "Up" (up-handler state board-state)]
           [button "Top" (top-handler state
                                      board-state)]
           [:br]
           [button "Win" (result-handler state :right)]
           [button "Lose" (result-handler state :wrong)]
           [button "Any Black" (any-handler state board-state)]]
          [:div.col-md-6
           [:h3 "Preview"]
           [(pr/problem
             resulting-problem
             identity)]]]
         [:h3 "Problem Data"]
         [:pre (with-out-str (pprint (dissoc resulting-problem
                                             :width)))]]))))
