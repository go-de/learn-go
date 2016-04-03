(ns learngo.view.board-test
  (:require [cljs.test          :refer-macros [is]]
            [devcards.core      :refer-macros [defcard-rg
                                               deftest
                                               start-devcard-ui!]]
            [learngo.view.board :as board-view]
            [reagent.core       :as r]))

(defcard-rg empty-board
  [board-view/board
   (r/atom {:width 200})
   {:on-click #(println "clicked" %)}])

(def sample-state
  {:stones {[15 14] :black
            [15 16] :white}
   :marks {[16 14] "A"
           [16 12] "B"
           [15 14] :circle
           [13 15] :triangle}
   :size 19
   :width 300})

(defcard-rg corner
  [board-view/board
   (r/atom (merge
            sample-state
            {:section {:top 7
                       :left 7}}))
   {}])

(defcard-rg put-black-on-click
  (let [state (r/atom sample-state)
        on-click #(swap! state assoc-in [:stones %] :black)]
    [board-view/board state {:on-click on-click}]))

(defcard-rg resize-on-click
  (let [state (r/atom sample-state)
        on-click #(swap! state update :width + 100)]
    [board-view/board state {:on-click on-click}]))

(defcard-rg cut-off-on-click
  (let [cutoff (r/atom 0)
        state (r/track (fn []
                         (let [cut @cutoff]
                           (assoc sample-state
                                  :section {:top cut
                                            :left cut
                                            :right cut
                                            :bottom cut}))))
        on-click #(swap! cutoff inc)]
    [board-view/board state {:on-click on-click}]))
