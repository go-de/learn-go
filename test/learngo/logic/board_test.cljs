(ns learngo.logic.board-test
  (:require [cljs.test           :refer-macros [is]]
            [devcards.core       :refer-macros [defcard-rg deftest]]
            [learngo.logic.board :as board]))

(deftest neighbours
  (is (= 2 (count (board/neighbours 9 [0 0])))))

(deftest liberties
  (let [bd (board/make-board 9
                             :black [[8 8]]
                             :white [[8 7]])]
    (is (= 1 (count (board/liberties bd [8 8])))))
  (let [bd (board/make-board 9
                             :black [[5 5] [3 4]]
                             :white [[3 3]])]
    (is (= 4 (count (board/liberties bd [5 5]))))
    (is (= 3 (count (board/liberties bd [3 4]))))
    (is (= 3 (count (board/liberties bd [3 3]))))))
