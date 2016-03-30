(ns learngo.editor
  (:require [cljs.pprint        :refer [pprint]]
            [learngo.board      :as bd]
            [learngo.buttons    :as buttons]
            [learngo.forms      :as forms]
            [learngo.i18n       :as i18n]
            [learngo.layout     :as layout]
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
  {:tool :black
   :path []
   :history []
   :size 9
   :top 0
   :left 0
   :right 0
   :bottom 0})

(defn make [init-state]
  (let [state (r/atom (merge editor-defaults init-state))
        board-state (r/atom (root-board-state state))
        after-play (after-play-handler state board-state)]
    (fn []
      (let [resulting-problem (dissoc @state :path :history :status :tool)
            tool->button {:black :add-black
                          :white :add-white
                          :play :play}
            button-state (reaction
                          (let [active-btn (-> @state
                                               :tool
                                               tool->button)
                                disable-any-black?
                                (or (= (:player @board-state) :white)
                                     (not= active-btn :play))]
                            (merge
                             {active-btn :active}
                             (when disable-any-black?
                               {:any-black :disabled}))))
            width @(r/track layout/board-width)]
        [:div
         [:h3 (i18n/translate :board-geometry)]
         [bind-fields (forms/geometry)
          state]
         [:h3 (i18n/translate :editor)]
         [:div.row
          [:div.col-md-6
           [(bd/board-wrapper
             (assoc @state :width width)
             board-state
             after-play)]]
          [:div.col-md-6
           [buttons/group
            [[:add-black (select-tool-handler state board-state :black)]
             [:add-white (select-tool-handler state board-state :white)]
             [:play (select-tool-handler state board-state :play)]
             [:up (up-handler state board-state)]
             [:top (top-handler state
                                board-state)]
             [:win (result-handler state :right)]
             [:lose (result-handler state :wrong)]
             [:any-black (any-handler state board-state)]]
            button-state]]]
         [:h2 (i18n/translate :preview)]
         [(pr/problem
           resulting-problem
           identity)]
         [:h3 (i18n/translate :problem-data)]
         [:pre (with-out-str (pprint resulting-problem))]]))))
