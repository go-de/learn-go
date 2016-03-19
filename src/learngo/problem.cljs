(ns learngo.problem
  (:require [clojure.string :as str]
            [learngo.board  :as bd]
            [reagent.core   :as r]))

(def feedback-delay 400)

(defn point-from-shorthand [kw]
  (let [[fst & rst] (-> kw name)
        x (- (.charCodeAt fst 0)
             (.charCodeAt "a" 0))
        y (->> rst (apply str) int dec)]
    [x y]))

(defn stones-from-shorthand [board]
  (into {}
        (for [[color positions] board
              pt positions]
          [(point-from-shorthand pt) color])))

(defn point-to-shorthand [[x y]]
  (keyword (str
            (char (+ x (.charCodeAt "a" 0)))
            (inc y))))

(defn get-key-or-any [m k]
  (if-let [v (m k)]
    [k v]
    [:any (:any m)]))

(defn delay-feedback [state {:keys [reply status]}]
  (js/setTimeout
   (fn []
     (when reply
       (bd/play! state reply))
     (if status
       (swap! state assoc :status status)
       (swap! state dissoc :disabled?)))
   feedback-delay))

(defn after-play-handler [state variations]
  (fn [pos]
    (let [{:keys [path]} @state
          current (->> path
                       (interpose :next)
                       (get-in variations))
          [move info] (get-key-or-any current pos)
          new-path (conj path move)]
      (swap! state assoc
             :disabled? true
             :path new-path)
      (delay-feedback state info))))

(defn problem [info]
  (let [{:keys [text stones variations]} info
        init (-> info
                 (assoc :width 300)
                 (dissoc :text :variations))
        state (r/atom {:stones stones
                       :path []})
        handler (after-play-handler state variations)]
    (fn []
      [:div
       [bd/board init state handler]
       (if-let [status (:status @state)]
         [:p (-> status
                 name
                 str/capitalize)]
         [:p text])])))
