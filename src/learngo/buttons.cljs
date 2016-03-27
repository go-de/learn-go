(ns learngo.buttons
  (:require [learngo.i18n     :as i18n]
            [learngo.ui-utils :as ui]))

(defn icon [icon alt-text handler]
  [:button.btn.btn-default.btn-lg {:type :button
                   :on-click handler
                   :title (i18n/translate alt-text)}
   [ui/glyphicon icon]])

(defn large-button [alt-text handler status]
  [:button.btn.btn-default {:type :button
                            :class status
                            :on-click (when (not= status :disabled)
                                        handler)
                            :title (i18n/translate alt-text)}
   (i18n/translate alt-text)])

(defn group [buttons state]
  [:div.btn-group {:role :group}
   (doall
    (for [[title handler] buttons]
      ^{:key title} [large-button title handler (get @state title)]))])
