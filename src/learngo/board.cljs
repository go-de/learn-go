(ns learngo.board
  (:require [clojure.data  :as data]
            [learngo.rules :as rules]
            [reagent.core  :as r]))

(enable-console-print!)

(defn make-object [[x y] item]
  (let [pos-map {:x x
                 :y y}
        item-map (cond (= item :black)    {:c js/WGo.B}
                       (= item :white)    {:c js/WGo.W}
                       (= item :circle)   {:type "CR"}
                       (= item :triangle) {:type "TR"}
                       (string? item)     {:type "LB" :text item})]
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

(defn play! [state move]
  (let [player (or (:player @state)
                   :black)
        board (rules/play @state player move)]
    (when board
      (reset! state
              (assoc board :player (rules/other-color player))))))

(defn play-handler [size state after-play on-click]
  (fn [x y]
    (swap! state assoc :size size)
    (when-not (:disabled? @state)
      (when-not (when on-click
                  (on-click [x y]))
        (when (play! state [x y])
          (after-play [x y]))))))

(defn board [{:keys [size width height top left right bottom]
              :or {size 19
                   top 0
                   left 0
                   right 0
                   bottom 0}}
             state
             after-play
             & [on-click]]
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
                                          :section {:top top
                                                    :left left
                                                    :right right
                                                    :bottom bottom}}))]
          (reset! bd-ref bd)
          (update-board bd nil @state)
          (reset! last-state @state)
          (.addEventListener
           bd "click"
           (play-handler size state after-play on-click))))
      :component-will-update
      (fn [_ _]
        (update-board @bd-ref @last-state @state)
        (reset! last-state @state))
      :reagent-render
      (fn []
        @state
        [:div.board])})))

(defn board-wrapper [init state after-play & [on-click]]
  (fn []
    [board init state after-play on-click]))
