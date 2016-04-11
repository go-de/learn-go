(ns learngo.forms-test
  (:require [cljs.test          :refer-macros [is]]
            [devcards.core      :refer-macros [defcard-rg deftest]]
            [learngo.view.forms :as forms]
            [reagent.core       :as r]
            [reagent-forms.core :refer [bind-fields]]))

(defcard-rg button-group
  [forms/button-group [[:foo #(.log js/console "foo") :active]
                       [:bar #(.log js/console "bar") :disabled]
                       [:baz #(.log js/console "baz") nil]]])

(defn sample-form []
  (let [state (r/atom {:size 19
                       :top 0
                       :left 0
                       :right 0
                       :bottom 0})]
    (fn []
      [:div.container
       [bind-fields
        (forms/geometry)
        state]])))

(defcard-rg sample-form
  [sample-form])

(defn sample-input []
  (let [state (r/atom {:text "Some Text"
                       :title "Some Title"})]
    (fn []
      [:div.container
       [bind-fields
        [:div
         (forms/input :text "Text")
         (forms/input :title "Title")]
        state]
       [:pre (str @state)]])))

(defcard-rg sample-input
  [sample-input])
