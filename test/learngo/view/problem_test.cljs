(ns learngo.view.problem-test
  (:require [cljs.test            :refer-macros [is]]
            [devcards.core        :refer-macros [defcard-rg
                                                 deftest]]
            [learngo.problems     :as probs]
            [learngo.view.problem :as problem-view]
            [reagent.core         :as r]))

(def print-nav-handlers
  {:on-reset #(println "reset")
   :on-nav #(println "nav" %)})

(defcard-rg nav-bar
  [problem-view/nav-bar
   (atom {})
   print-nav-handlers])

(defcard-rg icons
  [:div {:style {:height 50}}
   [problem-view/result-icon :right 50]
   [problem-view/result-icon :wrong 150]])

(defcard-rg problem-start
  [problem-view/problem
   probs/capture-1
   (assoc print-nav-handlers
          :on-click println)])

(defcard-rg problem-correct-move-no-feedback
  [problem-view/problem
   (assoc probs/capture-1
          :path [[4 3]]
          :hide-feedback? true)
   print-nav-handlers])

(defcard-rg problem-correct-move-with-feedback
  [problem-view/problem
   (assoc probs/capture-1
          :path [[4 3]]
          :show-feedback? true)
   print-nav-handlers])

(defcard-rg problem-wrong-move-with-feedback
  [problem-view/problem
   (assoc probs/capture-1
          :path [[6 6]]
          :show-feedback? true)
   print-nav-handlers])

(defcard-rg geta-prob-1
  [problem-view/problem
   probs/geta-1
   print-nav-handlers])

(defcard-rg sample-collection
  [problem-view/collection
   probs/all])
