(ns learngo.rules-test
  (:require [cljs.test     :refer-macros [is]]
            [devcards.core :refer-macros [defcard-rg
                                          deftest
                                          start-devcard-ui!]]
            [learngo.rules :as rules]))

(deftest neighbours
  (is (= 2 (count (rules/neighbours 9 [0 0])))))

(deftest liberties
  (let [bd (rules/make-board 9
                             :black [[8 8]]
                             :white [[8 7]])]
    (is (= 1 (count (rules/liberties bd [8 8])))))
  (let [bd (rules/make-board 9
                             :black [[5 5] [3 4]]
                             :white [[3 3]])]
    (is (= 4 (count (rules/liberties bd [5 5]))))
    (is (= 3 (count (rules/liberties bd [3 4]))))
    (is (= 3 (count (rules/liberties bd [3 3]))))))
