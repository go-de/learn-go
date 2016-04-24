(ns learngo.view.intro-test
  (:require [cljs.test          :refer-macros [is]]
            [devcards.core      :refer-macros [defcard-rg
                                               deftest]]
            [learngo.view.intro :as intro-view]
            [reagent.core       :as r]))

(deftest play-seq
  (is (= [{}
          {:stones {[0 0] :black}}]
         (->> [[0 0]]
              (intro-view/play-seq {:size 19})
              (map #(dissoc % :size)))))
  (is (= [{}
          {:stones {[0 1] :black}}
          {:stones {[0 0] :white
                    [0 1] :black}}
          {:stones {[1 0] :black
                    [0 1] :black}}]
         (->> [[0 1]
               [0 0]
               [1 0]]
              (intro-view/play-seq {:size 19})
              (map #(dissoc % :size :ko))))))

(defcard-rg pro-game
  [intro-view/pro-game])

(defcard-rg page
  [intro-view/page])
