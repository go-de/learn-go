(ns learngo.problem-test
  (:require [cljs.test       :refer-macros [is]]
            [devcards.core   :refer-macros [defcard-rg
                                            deftest
                                            start-devcard-ui!]]
            [learngo.problem :as p]
            [reagent.core    :as r]))

(deftest parse-shorthand
  (is (= [0 0] (p/point-from-shorthand :a1)))
  (is (= [18 18] (p/point-from-shorthand :s19))))

(deftest stones-from-shorthand
  (is (= {[3 4] :black
          [5 4] :black
          [4 4] :white}
         (p/stones-from-shorthand {:black [:d5 :f5]
                                   :white [:e5]}))))

(deftest point-to-shorthand
  (is (= :a1  (p/point-to-shorthand [0 0])))
  (is (= :s19 (p/point-to-shorthand [18 18]))))

(defn capture-prob-1 []
  (p/problem
   {:text "Capture the white stone"
    :size 9
    :top 1
    :left 1
    :bottom 1
    :right 1
    :stones {[3 4] :black
            [5 4] :black
            [4 5] :black
            [4 4] :white}
    :variations {[4 3] {:status :right}
                 :any  {:status :wrong
                        :reply  [4 3]}}}))

(defcard-rg capture-prob-1
  [capture-prob-1])
