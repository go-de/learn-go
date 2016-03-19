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

(def capture-descr-1
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
   :vars {[4 3] {:status :right}
                :any  {:status :wrong
                       :reply  [4 3]}}})

(defn capture-prob-1 []
  (p/problem
   capture-descr-1
   #(prn :nav %)))

(defcard-rg capture-prob-1
  [capture-prob-1])

(def geta-descr-1
  {:text "Capture the stone marked \"A\""
   :size 9
   :top 1
   :left 1
   :bottom 1
   :right 1
   :marks {[4 4] "A"}
   :stones {[3 4] :black
            [3 3] :black
            [4 5] :black
            [5 5] :black
            [4 4] :white
            [6 2] :white}
   :vars {[5 3] {:reply [4 3]
                 :vars {[4 2] {:reply [5 4]
                               :vars {[6 4] {:status :right}
                                      :any {:status :wrong
                                            :reply [6 4]}}}
                        :any {:status :wrong
                              :reply [4 2]}}}
          [5 4] {:status :wrong
                 :reply [4 3]}
          [4 3] {:status :wrong
                 :reply [5 4]}
          :any  {:status :wrong}}})

(defn geta-prob-1 []
  (p/problem
   geta-descr-1
   #(prn :nav %)))

(defcard-rg geta-prob-1
  [geta-prob-1])

(defn sample-collection-test []
  [p/collection
   [capture-descr-1
    geta-descr-1]])

(defcard-rg sample-collection
  [sample-collection-test])
