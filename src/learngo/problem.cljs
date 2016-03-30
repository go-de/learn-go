(ns learngo.problem
  (:require [clojure.string   :as str]
            [learngo.board    :as bd]
            [learngo.buttons  :as btn]
            [learngo.i18n     :as i18n]
            [learngo.layout   :as layout]
            [learngo.ui-utils :as ui]
            [reagent.core     :as r]))

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

(defn delay-feedback [state {:keys [reply status text]}]
  (js/setTimeout
   (fn []
     (when reply
       (bd/play! state reply)
       (swap! state assoc :marks {reply :circle}))
     (swap! state assoc :text (@i18n/language text))
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
  [:div.btn-group.problem-nav
   {:role :group}
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
  [:p.status-icon {:class status}
   [ui/glyphicon (case status
                   :right :ok
                   :wrong :remove)]])

(defn problem [info nav-handler]
  (let [{:keys [text title stones marks]} info
        init (-> info
                 (dissoc :text :vars))
        initial-state {:stones stones
                       :marks marks
                       :text (@i18n/language text)
                       :path []}
        state (r/atom initial-state)
        handler (after-play-handler state info)]
    (fn []
      (let [{:keys [status text]} @state
            width @(r/track layout/board-width)
            board-opts (assoc init :width width)]
        [:div.problem
         [:h3 (title @i18n/language)]
         [:div.row
          [:div.col-md-6.col-lg-5
           [(bd/board-wrapper board-opts state handler)]
           (when status
             [result-icon status])
           [nav-bar #(reset! state initial-state) nav-handler]]
          [:div.col-md-6
           [:p.problem-text text]]]]))))

(defn collection [descriptions]
  (let [nav-state (r/atom 0)
        nav-handler (fn [event]
                      (reset! nav-state
                              (case event
                                :next (min (inc @nav-state)
                                           (dec (count descriptions)))
                                :prev (max (dec @nav-state)
                                           0))))]
    (fn []
      [(problem
         (nth descriptions @nav-state)
         nav-handler)])))
