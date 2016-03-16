(ns learngo.board
  (:require [clojure.data :as data]
            [reagent.core :as r]))

(enable-console-print!)

(defn make-object [[x y] item]
  (let [pos-map {:x x
                 :y y}
        item-map (cond (= item :black)  {:c js/WGo.B}
                       (= item :white)  {:c js/WGo.W}
                       (= item :circle) {:type "CR"}
                       (string? item)   {:type "LB" :text item})]
    (clj->js
     (merge pos-map item-map))))

(defn make-objects [diff-map]
  (->> diff-map
       (map (fn [[pos item]]
              (make-object pos item)))
       clj->js))

(defn remove-all [bd diff-map]
  (.removeObject bd (make-objects diff-map)))

(defn add-all [bd diff-map]
  (.addObject bd (make-objects diff-map)))

(defn update-board [bd last-state current-state]
  (let [[stones-to-remove stones-to-add] (data/diff (:stones last-state)
                                                    (:stones current-state))
        [marks-to-remove marks-to-add] (data/diff (:marks last-state)
                                                  (:marks current-state))]
    (remove-all bd stones-to-remove)
    (remove-all bd marks-to-remove)
    (add-all bd stones-to-add)
    (add-all bd marks-to-add)))

(defn board [{:keys [size width height top left right bottom]
              :or {size 19
                   top 0
                   left 0
                   right 0
                   bottom 0}}
             state]
  (let [bd-ref (atom nil)
        last-state (atom nil)]
    (r/create-class
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
          (reset! bd-ref bd)
          (update-board bd nil @state)
          (reset! last-state @state)))
      :component-will-update
      (fn [_ [_ _ state]]
        (update-board @bd-ref @last-state @state)
        (reset! last-state @state))
      :reagent-render
      (fn [init state]
        @state
        [:div.board])})))
