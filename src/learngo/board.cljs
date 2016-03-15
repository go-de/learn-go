(ns learngo.board
  (:require [reagent.core :as r]))

(enable-console-print!)

(defn board [{:keys [size width height top left right bottom]
              :or {size 19
                   top 0
                   left 0
                   right 0
                   bottom 0}}
             state]
  (let [bd-ref (atom nil)]
    [(r/create-class
      {:component-did-mount
       (fn [obj]
         (let [node (js/ReactDOM.findDOMNode obj)
               bd (js/WGo.Board. node
                                 (clj->js {:height height
                                           :width width
                                           :size size
                                           :section (clj->js {:top top
                                                              :left left
                                                              :right right
                                                              :bottom bottom})}))]
           (reset! bd-ref bd)))
       :reagent-render
       (fn [state]
         [:div.board])})]))
