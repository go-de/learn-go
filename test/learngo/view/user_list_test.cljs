(ns learngo.view.user-list-test
  (:require [cljs.test :refer [is]]
            [devcards.core :refer-macros [defcard-rg deftest]]
            [learngo.view.user-bar :refer [user-bar]]
            [learngo.view.user-list :refer [user-list]]))

(defcard-rg user-bar
  [user-bar])

(defcard-rg user-list
  [user-list])
