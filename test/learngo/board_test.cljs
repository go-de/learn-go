(ns learngo.board-test
  (:require [cljs.test     :refer-macros [is]]
            [devcards.core :refer-macros [defcard-rg
                                           deftest
                                           start-devcard-ui!]]
            [learngo.board :as bd]
            [reagent.core  :as r]))

(defcard-rg board
  (bd/board {:size 19
             :width 400}
            nil))

(defcard-rg partial
  (bd/board {:size 19
             :width 300
             :top 7
             :left 7}
            nil))
