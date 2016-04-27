(ns learngo.view.page-test
  (:require [cljs.test         :as t]
            [devcards.core     :refer-macros [defcard-rg
                                              deftest]]
            [learngo.view.page :as page-view]))


(defcard-rg navbar
  [page-view/navbar "Learn Go" :home :foo :bar])
