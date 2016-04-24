(ns learngo.logic.sgf-test
  (:require [cljs.test         :refer-macros [is]]
            [devcards.core     :refer-macros [defcard-rg deftest]]
            [learngo.logic.sgf :as sgf]))

(deftest parse-coord
  (is (= 0 (sgf/parse-coord "a")))
  (is (= 3 (sgf/parse-coord "d"))))

(deftest parse-move
  (is (= [1 3] (sgf/parse-move "[bd]"))))

(deftest parse-game
  (is (= [[16 3] [3 2] [15 16]]
         (take 3 (sgf/parse-game sgf/shusaku-vs-shuwa)))))
