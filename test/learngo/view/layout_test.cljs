(ns learngo.view.layout-test
  (:require [cljs.test           :refer-macros [is]]
            [devcards.core       :refer-macros [defcard-rg
                                               deftest
                                               start-devcard-ui!]]
            [learngo.view.board  :as board-view]
            [learngo.view.layout :as layout]
            [reagent.core        :as r]))

(defcard-rg start-dimensions
  [:p (str (layout/calc-dimensions))])

(defn dynamic-dimensions []
  [:p (str @layout/dimensions)])

(defcard-rg dynamic-dimensions
  [dynamic-dimensions])

(defn dynamic-board []
  (let [width (r/track layout/board-width)
        state (r/track (fn []
                         {:stones {[4 4] :black}
                          :marks {[4 4] "S"}
                          :width @width}))]
    (fn []
      [board-view/board
       state
       {}])))

(defcard-rg dynamic-board
  [dynamic-board])
