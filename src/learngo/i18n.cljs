(ns learngo.i18n
  (:require [clojure.string              :as str]
            [learngo.translations.german :as de]))

(def language (atom :de))

(def languages
  {:de de/translate})

(defn default-translation [key]
  (println "Warning: No translation for" key)
  (-> key
      name
      (str/replace #"-" " ")))

(defn translate [key]
  (let [translate (languages @language)]
    (or (translate key)
        (default-translation key))))
