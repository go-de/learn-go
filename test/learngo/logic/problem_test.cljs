(ns learngo.logic.problem-test
  (:require [cljs.test             :refer-macros [is]]
            [devcards.core         :refer-macros [defcard-rg deftest]]
            [learngo.logic.problem :as problem]
            [learngo.problems      :as probs]
            [reagent.core          :as r]))

(deftest parse-shorthand
  (is (= [0 0]   (problem/point-from-shorthand :a1)))
  (is (= [18 18] (problem/point-from-shorthand :s19))))

(deftest stones-from-shorthand
  (is (= {[3 4] :black
          [5 4] :black
          [4 4] :white}
         (problem/stones-from-shorthand {:black [:d5 :f5]
                                       :white [:e5]}))))

(deftest point-to-shorthand
  (is (= :a1  (problem/point-to-shorthand [0 0])))
  (is (= :s19 (problem/point-to-shorthand [18 18]))))

(deftest path-node
  (is (= probs/capture-1
         (problem/path-node probs/capture-1 [])))
  (is (= {:status :right
          :text {:de "Gut gemacht!"}}
         (problem/path-node probs/capture-1 [[4 3]])))
  (is (= {:status :wrong
          :text {:de "Probier's nochmal"}
          :reply  [4 3]}
         (problem/path-node probs/capture-1 [[1 1]]))))

(deftest current-moves
  (is (empty? (problem/current-moves probs/capture-1)))
  (is (= [[:black [4 3]]]
         (-> probs/capture-1
             (assoc :path [[4 3]]
                    :show-feedback? true)
             problem/current-moves)))
  (is (= [[:black [2 3]]
          [:white [4 3]]]
         (-> probs/capture-1
             (assoc :path [[2 3]]
                    :show-feedback? true)
             problem/current-moves))))

(deftest current-board
  (let [problem (-> probs/capture-1
                    (select-keys [:stones])
                    (assoc :marks {[0 0] "A"})
                    (assoc :size 9))]
    (is (= {:stones {[3 4] :black
                     [5 4] :black
                     [4 5] :black
                     [4 4] :white}
            :marks {[0 0] "A"}}
           (-> problem
               problem/current-board
               (select-keys [:stones :marks]))))
    (is (= {:stones {[3 4] :black
                     [5 4] :black
                     [4 5] :black
                     [4 3] :black}}
           (-> problem
               (assoc :path [[4 3]])
               problem/current-board
               (select-keys [:stones :marks]))))))

(deftest section-around
  (is (= {:top 0
          :bottom 0
          :left 0
          :right 0}
         (problem/section-around {:size 9}
                                 [4 4]
                                 4)))
  (is (= {:top 1
          :bottom 1
          :left 1
          :right 1}
         (problem/section-around {:size 9}
                                 [4 4]
                                 3)))
  (is (= {:top 1
          :bottom 1
          :left 0
          :right 2}
         (problem/section-around {:size 9}
                                 [3 4]
                                 3)))
  (is (= {:top 2
          :bottom 0
          :left 2
          :right 0}
         (problem/section-around {:size 9}
                                 [8 7]
                                 3))))
