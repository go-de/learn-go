(ns learngo.view.utils
  (:require [clojure.string :as str]))

(defn classes [& args]
  (->> args
       flatten
       (map name)
       (str/join " ")))

(defn glyphicon [icon]
  [:span.glyphicon {:class (str "glyphicon-" (name icon))}])
