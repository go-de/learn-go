(ns learngo.db.problem-test
  (:require [cljs.test :refer [is]]
            [devcards.core :refer-macros [deftest defcard-rg]]
            [learngo.db.problem :as problem]
            [learngo.view.user-bar :refer [user-bar]]))

(defcard-rg user-bar
  [user-bar])
