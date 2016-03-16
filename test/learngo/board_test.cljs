(ns learngo.board-test
  (:require [cljs.test     :refer-macros [is]]
            [devcards.core :refer-macros [defcard-rg
                                           deftest
                                          start-devcard-ui!]]
            [learngo.board :as bd]
            [reagent.core  :as r]))

(def sample-state
  (atom {:stones {[14 14] :black
                  [14 16] :white}}))

(defcard-rg board
  [bd/board {:size 19
             :width 400}
   (atom nil)])

(defcard-rg partial
  [bd/board {:size 19
             :width 300
             :top 7
             :left 7}
   sample-state])

(defcard-rg marks
  [bd/board {:size 19
             :width 300
             :top 7
             :left 7}
   (atom {:stones {[15 15] :black
                   [15 16] :white}
          :marks {[15 15] "A"
                  [15 16] :circle
                  [15 17] "B"}})])
