(ns learngo.page
  (:require [clojure.string         :as str]
            [goog.events            :as events]
            [goog.history.EventType :as EventType]
            [learngo.editor         :as editor]
            [learngo.problem        :as problem]
            [learngo.problems       :as problems]
            [reagent.core           :as r]
            [secretary.core         :as secretary :refer-macros [defroute]])
  (:import goog.History))

(defonce current-page (r/atom :home))


(secretary/set-config! :prefix "#")

(defroute default "/" []
  (reset! current-page :home))

(defroute home "/home" []
  (reset! current-page :home))

(defroute tutorial "/tutorial" []
  (reset! current-page :tutorial))

(defroute history "/history" []
  (reset! current-page :history))

(defroute links "/links" []
  (reset! current-page :links))

(defroute contribute "/contribute" []
  (reset! current-page :contribute))

(defroute "*" []
  (reset! current-page :not-found))

(let [h (History.)]
  (goog.events/listen h EventType/NAVIGATE #(secretary/dispatch! (.-token %)))
  (doto h (.setEnabled true))
  (defn navigate [item]
    (let [url (str "#/" (-> item name str/lower-case))]
      (.pushState js/history #js {} "" url)
      (secretary/dispatch! url))))

(def translate (comp #(str/replace % #"-" " ") name))

(defn home-page []
  [:div.container-fluid
   [:h1 (translate :go-is-awesome)]
   [:button.btn.btn-primary {:type :button
                             :on-click #(navigate :tutorial)}
    (translate :learn!)]])

(defn navbar [page-name & items]
  [:nav {:class "navbar navbar-default"}
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand page-name]]
    [:ul {:class "nav navbar-nav"}
     (doall
      (for [item items]
        [:li {:key item
              :class (when (= @current-page item)
                       "active")
              :style {:cursor :pointer}}
         [:a {:on-click #(navigate item)}
          (name item)]]))]]])

(defn problem-page []
  [problem/collection
   problems/all])

(defn history-page []
  [:div.container-fluid
   [:h1 (translate :go-history)]
   [:p (translate :lorem-ipsum)]])

(defn contribute-page []
  [:div.container-fluid
   [:h1 (translate :contribute)]
   [:a {:href "https://github.com/go-de/learn-go"}
    (translate :fork-us-on-github)]
   [:p (translate :or-build-and-send-a-problem)]
   [:h2 "Problem Editor"]
   [editor/make {:size 9
                 :text "Problem description comes here..."}]])

(defn links-page []
  [:div.container-fluid
   [:h1 (translate :useful-links)]
   [:a {:href "http://www.dgob.de"} (translate :dgob)]])

(defn content []
  [:div
   [navbar "Learn Go" :home :tutorial :history :links :contribute]
   [:div
    (case @current-page
      :home [home-page]
      :tutorial [problem-page]
      :history [history-page]
      :contribute [contribute-page]
      :links [links-page]
      [:h1 "Or else..."])]])
