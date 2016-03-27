(ns learngo.problem
  (:require [clojure.string  :as str]
            [learngo.board   :as bd]
            [learngo.buttons :as btn]
            [reagent.core    :as r]
            [learngo.i18n    :as i18n]))

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
       (bd/play! state reply)
       (swap! state assoc :marks {reply :circle}))
     (if status
       (swap! state assoc :status status)
       (swap! state dissoc :disabled?)))
   feedback-delay))

(defn after-play-handler [state info]
  (fn [pos]
    (swap! state assoc :marks {pos :circle})
    (let [{:keys [path]} @state
          current (->> path
                       (mapcat #(vector :vars %))
                       (get-in info))
          [move info] (get-key-or-any (:vars current)
                                      pos)
          new-path (conj path move)]
      (swap! state assoc
             :disabled? true
             :path new-path)
      (delay-feedback state info))))

(defn nav-bar [reset-handler nav-handler]
  [:div.btn-group {:role :group
                   :style {:position :absolute
                           :margin-top "5px"}}
   [btn/icon
    :arrow-left
    :previous-problem
    (partial nav-handler :prev)]
   [btn/icon
    :repeat
    :restart-problem
    reset-handler]
   [btn/icon
    :arrow-right
    :next-problem
    (partial nav-handler :next)]])

(defn result-icon [status]
  [:p {:style {:margin-left 360
               :font-size "40px"}}
   [:span.result-icon.glyphicon
    {:title (i18n/translate status)
     :class (case status
              :right :glyphicon-ok
              :wrong :glyphicon-remove)
     :style {:color (case status
                      :right :green
                      :wrong :red)}}]])

(defn problem [info nav-handler]
  (let [{:keys [text stones marks]} info
        init (-> info
                 (assoc :width 400)
                 (dissoc :text :vars))
        initial-state {:stones stones
                       :marks marks
                       :path []}
        state (r/atom initial-state)
        handler (after-play-handler state info)]
    (fn []
      (let [status (:status @state)]
        [:div.row
         [:div.col-xs-12
          [:p.lead text]
          [bd/board init state handler]
          [nav-bar #(reset! state initial-state) nav-handler]
          (when status
            [result-icon status])]]))))

(defn collection [descriptions]
  (let [nav-state (r/atom 0)
        nav-handler (fn [event]
                      (reset! nav-state
                              (case event
                                :next (min (inc @nav-state)
                                           (dec (count descriptions)))
                                :prev (max (dec @nav-state)
                                           0))))]
    (fn [descriptions]
      [(problem
         (nth descriptions @nav-state)
         nav-handler)])))
