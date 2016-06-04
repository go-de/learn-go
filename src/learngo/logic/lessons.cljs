(ns learngo.logic.lessons
  (:require [learngo.problems.lesson-1 :as lesson-1]
            [learngo.problems.lesson-2 :as lesson-2]))

(defn all []
  (->> [{:title {:de "Einführung"}}
        lesson-1/data]
       (map-indexed (fn [i data]
                      (assoc data :number i)))))
