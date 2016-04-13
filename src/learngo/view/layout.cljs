(ns learngo.view.layout
  (:require [reagent.core :as reagent]))

(defn calc-dimensions []
  (let [w js/window
        d js/document
        e (.-documentElement d)
        g (-> d (.getElementsByTagName "body") (aget 0))
        x (or (.-innerWidth w)
              (.-clientWidth e)
              (.-clientWidth g))
        y (or (.-innerHeight w)
              (.-clientHeight e)
              (.-clientHeight g))]
    [x y]))

(def dimensions (reagent/atom (calc-dimensions)))

(.addEventListener
 js/window
 "resize"
 #(reset! dimensions (calc-dimensions)))

(defn board-width []
  (let [[x y] @dimensions]
    (- (min 500 x y)
       40)))
