(ns learngo.i18n
  (:require [clojure.string              :as str]
            [learngo.translations.german :as de]
            [taoensso.timbre             :refer-macros [warn]]))

(def language (atom :de))

(def languages
  {:de de/translate})

(defn default-translation [key]
  (warn "Warning: No translation for" key)
  (-> key
      name
      (str/replace #"-" " ")))

(defn translate [key]
  (let [translate (languages @language)]
    (or (translate key)
        (default-translation key))))

(defn from-map [m]
  (get m @language))
