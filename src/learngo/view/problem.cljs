(ns learngo.view.problem
  (:require [learngo.i18n          :as i18n]
            [learngo.logic.board   :as board]
            [learngo.logic.problem :as problem]
            [learngo.view.board    :as board-view]
            [learngo.view.layout   :as layout]
            [learngo.view.utils    :as ui]
            [reagent.core          :as r]
            [taoensso.timbre       :refer-macros [debug]]))

(defn nav-icon [icon alt-text classes handler]
  [:button.btn.btn-default.btn-lg {:type :button
                                   :on-click handler
                                   :title (i18n/translate alt-text)
                                   :class (ui/classes classes)}
   [ui/glyphicon icon]])

(defn nav-bar [state {:keys [on-reset on-nav]}]
  [:div.btn-group.problem-nav
   {:role :group}
   [nav-icon
    :arrow-left
    :previous-problem
    []
    (partial on-nav :prev)]
   [nav-icon
    :repeat
    :restart-problem
    []
    on-reset]
   [nav-icon
    :arrow-right
    :next-problem
    []
    (partial on-nav :next)]])

(defn result-glyphicon [status]
  [:span.status {:class status}
   [ui/glyphicon (case status
                   :right :ok
                   :wrong :remove)]])

(defn result-icon [status board-width]
  [:p.status-icon
   {:style {:margin-left (str (- board-width 40)
                              "px")}}
   [result-glyphicon status]])

(def feedback-delay 400)

(defn play! [state move]
  (when-let [new-state (problem/play-and-disable @state move)]
    (reset! state new-state)
    (js/setTimeout
     #(swap! state problem/show-feedback-and-enable)
     feedback-delay)))

(defn problem [definition handlers]
  (debug "rendering problem" definition)
  (let [state (r/atom (merge {:size 9} definition))
        nav-state (atom nil)
        nav-handlers (merge
                      {:on-nav identity
                       :on-reset #(swap! state assoc :path [])}
                      handlers)
        width (r/track layout/board-width)
        board (r/track (fn []
                         (assoc (problem/current-board @state)
                                :width @width)))
        node  (r/track (fn []
                         (problem/current-node @state)))]
    (fn []
      (debug "updating problem")
      (let [{:keys [title hide-feedback?]} @state
            title (get title @i18n/language)
            {:keys [status text]} @node
            text (get text @i18n/language)]
        [:div.problem
         [:div.row
          [:div.col-md-6.col-md-push-6.col-lg-7
           [:h3 title]
           [:p.problem-text (when-not hide-feedback?
                             text)]]
          [:div.col-md-6.col-md-pull-6.col-lg-5
            [board-view/board board {:on-click #(play! state %)}]
            (when (and status (not hide-feedback?))
              [result-icon status @width])
            [nav-bar nav-state nav-handlers]]
           ]]))))

(defn collection [descriptions]
  (debug "rendering collection")
  (let [nav-state (r/atom 0)
        nav-handler (fn [event]
                      (prn :nav event)
                      (reset! nav-state
                              (case event
                                :next (min (inc @nav-state)
                                           (dec (count descriptions)))
                                :prev (max (dec @nav-state)
                                           0))))]
    (fn []
      (debug "updating collection - problem " @nav-state)
      [(problem
        (nth descriptions @nav-state)
        {:on-nav nav-handler})])))
