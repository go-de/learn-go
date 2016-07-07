(ns learngo.navigation
  (:require [clojure.string         :as str]
            [goog.events            :as events]
            [goog.history.EventType :as EventType]
            [learngo.state          :as state]
            [secretary.core         :as secretary :refer-macros [defroute]])
  (:import goog.History))

(secretary/set-config! :prefix "#")

(defn as-str [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defroute "/" []
  (reset! state/current-page :home))

(defroute "/home" []
  (reset! state/current-page :home))

(defroute "/tutorial" []
  (reset! state/current-page :tutorial)
  (reset! state/current-lesson nil)
  (reset! state/current-problem nil))

(defroute "/tutorial/:lesson/:problem" [lesson problem]
  (reset! state/current-page :tutorial)
  (reset! state/current-lesson (js/parseInt lesson))
  (reset! state/current-problem (js/parseInt problem)))

(defroute "/about-go" []
  (reset! state/current-page :about-go))

(defroute "/links" []
  (println "links!")
  (reset! state/current-page :links))

(defroute "/contribute" []
  (reset! state/current-page :contribute))

(defroute "/contact" []
  (reset! state/current-page :contact))

(defroute "*" []
  (reset! state/current-page :not-found))

(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true))
  (defn go [& path]
    (let [url (->> path
                   (map (comp str/lower-case as-str))
                   (cons "#")
                   (str/join "/"))]
      (.pushState js/history #js {} "" url)
      (secretary/dispatch! url))))
