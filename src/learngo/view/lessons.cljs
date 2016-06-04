(ns learngo.view.lessons
  (:require [learngo.i18n          :as i18n]
            [learngo.logic.lessons :as lessons]))

(defn overview []
  [:div.lesson-overview
   [:h2 (i18n/translate :lesson-overview)]
   (into [:div.lessons.list.group]
         (for [{:keys [title problems number]} (lessons/all)]
           (let [problem-count (count problems)]
             [:a.lesson.list-group-item
              {:href "#"}
              (when (pos? problem-count)
                [:span.badge (count problems)])
              (str number ". " (i18n/from-map title))])))])
