(ns learngo.buttons
  (:require [learngo.i18n :as i18n]))

(defn icon [icon alt-text handler]
  [:button.btn.btn-default.btn-lg {:type :button
                   :on-click handler
                   :title (i18n/translate alt-text)}
   [:span.glyphicon {:class (str "glyphicon-" (name icon))}]])
