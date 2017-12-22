(ns learngo.view.user-bar-test
  (:require [cljs.test :refer [is]]
            [devcards.core :refer-macros [deftest defcard-rg]]
            [learngo.view.user-bar :refer [user-bar]]))

(defcard-rg user-bar
  [user-bar])
