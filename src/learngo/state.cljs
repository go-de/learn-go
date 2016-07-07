(ns learngo.state
  (:require [reagent.core :as r]))

(defonce current-page (r/atom :home))
(defonce current-lesson (r/atom nil))
(defonce current-problem (r/atom nil))
