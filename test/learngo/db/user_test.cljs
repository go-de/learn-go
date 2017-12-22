(ns learngo.db.user-test
  (:require  [cljs.test :refer [is]]
             [devcards.core :refer-macros [defcard-rg deftest]]
             [learngo.view.user-bar :refer [user-bar]]))

(defcard-rg user-bar
  [user-bar])
