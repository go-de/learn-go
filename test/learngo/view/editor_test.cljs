(ns learngo.view.editor-test
  (:require [cljs.test           :refer-macros [is]]
            [devcards.core       :refer-macros [defcard-rg
                                                deftest
                                                start-devcard-ui!]]
            [learngo.view.editor :as editor]
            [reagent.core        :as r]))

(defn text-input []
  (let [state (r/atom {:path []})]
    (fn []
      [:div
       [editor/text-input state]
       [editor/data state]
       [:p "Path:"]
       [:pre (str (:path @state))]
       [:button.button {:on-click #(swap! state update :path conj :any)} "Add"]
       [:button.button {:on-click #(swap! state update :path pop)} "Remove"]])))

(defcard-rg text-input
  [text-input])

(defn empty-board []
  (let [state (r/atom {})]
    [:div
     [editor/board state]
     [editor/data state]]))

(defcard-rg empty-board
  [empty-board])

(defn example-editor-test []
  [editor/editor {}])

(defcard-rg example-editor
  [example-editor-test])
