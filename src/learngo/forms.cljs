(ns learngo.forms
  (:require [learngo.i18n :as i18n]))

(defn size-slider [id caption & {:keys [min max]
                                 :or {min 0
                                      max 19}}]
  [:div.row
   [:div.col-sm-4
    [:label {:for id} caption]]
   [:div.col-sm-4
    [:input.form-control {:field :range
                          :min min
                          :max max
                          :id id}]]
   [:div.col-sm-2
    [:label {:field :label :id id}]]])

(defn geometry []
  [:div
   (size-slider :size (i18n/translate :board-size) :min 2)
   (size-slider :top (i18n/translate :top-cutoff) :max 15)
   (size-slider :left (i18n/translate :left-cutoff) :max 15)
   (size-slider :bottom (i18n/translate :bottom-cutoff) :max 15)
   (size-slider :right (i18n/translate :right-cutoff) :max 15)])

(defn input [id caption]
  [:div.row
   [:div.col-sm-4
    [:label {:for id} caption]]
   [:div.col-sm-4
    [:input.form-control {:field :text
                          :id id}]]])
