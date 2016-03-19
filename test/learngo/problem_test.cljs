(ns learngo.problem-test
  (:require [cljs.test        :refer-macros [is]]
            [devcards.core    :refer-macros [defcard-rg
                                            deftest
                                             start-devcard-ui!]]
            [learngo.problem  :as p]
            [learngo.problems :as ps]
            [reagent.core     :as r]))

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
   ps/capture-1
   #(prn :nav %)))

(defcard-rg capture-prob-1
  [capture-prob-1])

(defn geta-prob-1 []
  (p/problem
   ps/geta-1
   #(prn :nav %)))

(defcard-rg geta-prob-1
  [geta-prob-1])

(defn sample-collection-test []
  [p/collection
   ps/all])

(defcard-rg sample-collection
  [sample-collection-test])
