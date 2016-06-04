(ns learngo.view.lessons-test
  (:require [cljs.test            :refer-macros [is]]
            [devcards.core        :refer-macros [defcard-rg
                                                 deftest]]
            [learngo.view.lessons :as lessons-view]
            [reagent.core         :as r]))


(defcard-rg lessons
  [lessons-view/overview])
