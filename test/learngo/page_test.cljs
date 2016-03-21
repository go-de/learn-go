(ns learngo.page-test
  (:require [cljs.test     :as t]
            [devcards.core :refer-macros [defcard-rg
                                          deftest]]
            [learngo.page  :as page]))


(defcard-rg navbar
  [page/navbar "Learn Go" :home :foo :bar]
  )
