(ns learngo.problems
  (:require [learngo.problems.lesson-1 :as lesson-1]
            [learngo.problems.lesson-2 :as lesson-2]))

(def all
  (->> [lesson-1/data]
       (map :problems)
       (apply concat)))
