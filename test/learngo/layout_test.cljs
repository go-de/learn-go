(ns learngo.layout-test
  (:require [cljs.test      :refer-macros [is]]
            [devcards.core  :refer-macros [defcard-rg
                                           deftest
                                           start-devcard-ui!]]
            [learngo.board  :as bd]
            [learngo.layout :as layout]
            [reagent.core   :as r]))

(defcard-rg start-dimensions
  [:p (str (layout/calc-dimensions))])

(defn dynamic-dimensions []
  [:p (str @layout/dimensions)])

(defcard-rg dynamic-dimensions
  [dynamic-dimensions])

(defn dynamic-board []
  (let [width @(r/track layout/board-width)]
    [(bd/board-wrapper
      {:width width}
      (atom {:stones {[9 9] :black}
             :marks {[9 9] "S"}})
      identity)]))

(defcard-rg dynamic-board
  [dynamic-board])
