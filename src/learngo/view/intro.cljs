(ns learngo.view.intro
  (:require [learngo.i18n        :as i18n]
            [learngo.logic.board :as board]
            [learngo.logic.sgf   :as sgf]
            [learngo.view.board  :as board-view]
            [reagent.core        :as reagent]))

(def play-interval 4000)

(defn play-seq [board moves]
  (->> moves
       (map vector (cycle [:black :white]))
       (reductions
        (fn [board [player move]]
          (board/play board player move))
        board)))

(defn pro-game []
  (reagent/with-let [boards (->> sgf/shusaku-vs-shuwa
                                 sgf/parse-game
                                 (play-seq {:size 19})
                                 cycle
                                 reagent/atom)
                     board (reagent/track #(-> @boards
                                               first
                                               (assoc :width 500)))
                     interval (js/setInterval #(swap! boards rest)
                                              play-interval)]
    [board-view/board board]
    (finally
      (js/clearInterval interval))))

(defn page [{:keys [on-next]}]
  [:div
   [:div.row
    [:div.col-lg-6
     [:div.thumbnail
      [pro-game]
      [:div.caption
       [:h3 (i18n/translate :intro-game-caption)]
       [:p (i18n/translate :intro-game-description)]]]]
    [:div.col-lg-6
     [:p.lead (i18n/translate :intro-rules-text)]
     [:ol.lead
      [:li (i18n/translate :intro-rule-1)]
      [:li (i18n/translate :intro-rule-2)]
      [:li (i18n/translate :intro-rule-3)]]
     [:button.button {:on-click on-next}
      (i18n/translate :next)]]]])

(def data
  {:title {:de "Einführung"}
   :problems [[page {}]]})
