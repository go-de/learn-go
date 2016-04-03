(ns learngo.view.board
  (:require [clojure.data    :as data]
            [reagent.core    :as r]
            [taoensso.timbre :refer-macros [debug]]))

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

(defn deep-merge [a b]
  (merge-with (fn [x y]
                (cond (map? y) (deep-merge x y)
                      (vector? y) (concat x y)
                      :else y))
              a b))

(def geometry-defaults
  {:size 9
   :section {:top 0
             :left 0
             :right 0
             :bottom 0}})

;; The component needs to be remounted on geometry change
;; so it's a different argument in raw-board
(defn raw-board [geometry
                 state
                 on-click]
  (let [bd-ref (atom nil)
        last-state (atom nil)
        geometry (deep-merge geometry-defaults geometry)]
    (debug "rendering board")
    (r/create-class
     {:component-did-mount
      (fn [obj]
        (let [node (js/ReactDOM.findDOMNode obj)
              bd (js/WGo.Board. node
                                (clj->js geometry))
              click-handler (fn [x y]
                              (on-click [x y]))]
          (reset! bd-ref bd)
          (update-board bd nil @state)
          (reset! last-state @state)
          (when on-click
            (.addEventListener bd "click" click-handler))))
      :component-will-update
      (fn [_ _]
        (update-board @bd-ref @last-state @state)
        (reset! last-state @state))
      :reagent-render
      (fn []
        (debug "updating board" @state)
        @state
        [:div.board])})))

(defn geometry [board]
  (select-keys board [:size :section :width]))

(defn board [state {:keys [on-click]}]
  (let [geom (r/track #(geometry @state))]
    (fn []
      [(raw-board @geom state on-click)])))
