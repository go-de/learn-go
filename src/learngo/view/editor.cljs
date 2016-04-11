(ns learngo.view.editor
  (:require [cljs.pprint           :refer [pprint]]
            [learngo.i18n          :as i18n]
            [learngo.logic.board   :as board]
            [learngo.logic.editor  :as editor]
            [learngo.logic.problem :as problem]
            [learngo.view.board    :as board-view]
            [learngo.view.forms    :as forms]
            [learngo.view.layout   :as layout]
            [learngo.view.problem  :as problem-view]
            [reagent.core          :as r]
            [reagent.ratom         :refer-macros [reaction]]))

(defn board [state]
  (let [current-board (r/track #(problem/current-board @state))
        current-width (r/track layout/board-width)
        board (r/track #(assoc @current-board :width @current-width))
        on-click #(swap! state editor/apply-tool %)]
    (fn []
      [board-view/board board {:on-click on-click}])))

(defn tool-buttons [state]
  (let [current-tool (:tool @state)
        select #(swap! state editor/select-tool %)
        status #(when (= % current-tool)
                  :active)]
    [forms/button-group
     (for [tool [:add-black :add-white :play]]
       ^{:key tool}
       [tool #(select tool) (status tool)])]))

(defn tree-buttons [state]
  (let [status (when (empty? (:path @state))
                 :disabled)]
    [forms/button-group
     [[:up #(swap! state editor/up) status]
      [:top #(swap! state editor/top) status]
      [:delete-move #(swap! state editor/delete-current-move) status]]]))

(defn any-buttons [state]
  (let [player (:player @state)]
    [forms/button-group
     [[:any-black
        #(swap! state editor/play-any)
        (when (= player :white)
          :disabled)]
      [:refutes-other-moves identity nil]]]))

(defn result-buttons [state]
  (let [current-result (editor/status @state)
        status #(when (= % current-result)
                  :active)]
    [forms/button-group
     [[:no-result-yet #(swap! state editor/set-status nil) (status nil)]
      [:win #(swap! state editor/set-status :right) (status :right)]
      [:lose #(swap! state editor/set-status :wrong) (status :wrong)]]]))

(defn title-input [state]
  [forms/atom-input (i18n/translate :title)
   (r/cursor state [:title :de])])

(defn text-input [state]
  (let [var-path (r/track #(editor/current-var-path @state))]
    (fn []
      (let [node (r/cursor state @var-path)]
        [forms/atom-input (i18n/translate :node-text)
         (r/cursor node [:text :de])]))))

(defn controls [state]
  [:div
   [tool-buttons state] [:br]
   [tree-buttons state] [:br]
   [any-buttons state] [:br]
   [result-buttons state] [:br]
   [title-input state] [:br]
   [text-input state]])

(defn preview [state]
  (let [problem (r/track #(editor/->problem @state))]
    (fn []
      [(problem-view/problem @problem {})])))

(defn data [state]
  (let [problem (r/track #(editor/->problem @state))]
    (fn []
      [:pre (with-out-str (pprint @problem))])))

(defn editor [description]
  (let [state (r/atom (merge {:tool :add-black
                              :size 9}
                             description))]
    [:div.panel-group
     [:div.panel.panel-default
      [:div.panel-heading
       [:h4.panel-title
        [:a (i18n/translate :editor)]]]
      [:div.panel-body
       [:div.row
        [:div.col-md-6.col-lg-5
         [board state]]
        [:div.col-md-6.col-sm-8.col-xs-9
         [controls state]]]]]
     [:div.panel.panel-default
      [:div.panel-heading
       [:h4.panel-title
        [:a (i18n/translate :preview)]]]
      [:div.panel-body
       [preview state]]]
     [:div.panel.panel-default
      [:div.panel-heading
       [:h4.panel-title
        [:a (i18n/translate :problem-data)]]]
      [:div.panel-body
       [data state]]]]))
