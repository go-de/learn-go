(ns learngo.forms-test
  (:require [cljs.test          :refer-macros [is]]
            [devcards.core      :refer-macros [defcard-rg
                                           deftest
                                               start-devcard-ui!]]
            [learngo.forms      :as forms]
            [reagent.core       :as r]
            [reagent-forms.core :refer [bind-fields]]))

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
