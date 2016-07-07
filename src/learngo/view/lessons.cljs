(ns learngo.view.lessons
  (:require [learngo.i18n              :as i18n]
            [learngo.problems.lesson-1 :as lesson-1]
            [learngo.problems.lesson-2 :as lesson-2]
            [learngo.navigation        :as navigation]
            [learngo.state             :as state]
            [learngo.view.intro        :as intro]
            [learngo.view.problem      :as problem-view]))

(defn all []
  (->> [intro/data
        lesson-1/data
        lesson-2/data]
       (map-indexed (fn [i data]
                      (assoc data :number i)))))

(defn overview []
  [:div.lesson-overview
   [:h2 (i18n/translate :lesson-overview)]
   (into [:div.lessons.list.group]
         (for [{:keys [title problems number]} (all)]
           (let [problem-count (count problems)]
             [:a.lesson.list-group-item
              {:on-click (fn []
                           (navigation/go :tutorial number 0))}
              (when (pos? problem-count)
                [:span.badge (count problems)])
              (str number ". " (i18n/from-map title))])))])

(defn problem [lesson-number problem-number]
  (let [{:keys [title problems number]} (nth (all) lesson-number)
        problem (nth problems problem-number)]
    [:div
     [:h1 (i18n/from-map title)]
     (if (map? problem)
       [(problem-view/problem problem {})]
       problem)]))

(defn component []
  (let [lesson-index @state/current-lesson
        problem-index @state/current-problem]
    (if (and lesson-index problem-index)
      [problem lesson-index problem-index]
      [overview])))
