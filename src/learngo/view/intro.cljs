(ns learngo.view.intro
  (:require [learngo.logic.board :as board]
            [learngo.logic.sgf   :as sgf]
            [learngo.view.board  :as board-view]
            [reagent.core        :as reagent]))

(def play-interval 500)

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
