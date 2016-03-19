(ns learngo.board-test
  (:require [cljs.test     :refer-macros [is]]
            [devcards.core :refer-macros [defcard-rg
                                           deftest
                                          start-devcard-ui!]]
            [learngo.board :as bd]
            [learngo.rules :as rules]
            [reagent.core  :as r]))

(defn print-on-click [pt]
  (println "played" pt))

(defn board-example []
  [bd/board
   {:width 400}
   (atom nil)
   print-on-click])

(defcard-rg board
  [board-example])

(defn partial-example []
  [bd/board {:size 19
             :width 300
             :top 7
             :left 7}
   (r/atom {:stones {[14 14] :black
                   [14 16] :white}})
   print-on-click])

(defcard-rg partial
  [partial-example])

(defn mark-example []
  (let [state (r/atom {:stones {[15 15] :black
                                [15 16] :white}
                       :marks {[15 15] "A"
                               [15 16] :circle
                               [15 17] "B"
                               [17 17] :triangle}})]
    [bd/board {:size 19
               :width 300
               :top 7
               :left 7}
     state
     (fn [_]
       (swap! state assoc :disabled? true))]))

(defcard-rg marks
  [mark-example])
