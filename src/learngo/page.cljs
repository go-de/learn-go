(ns learngo.page
  (:require [clojure.string         :as str]
            [goog.events            :as events]
            [goog.history.EventType :as EventType]
            [learngo.editor         :as editor]
            [learngo.i18n           :as i18n]
            [learngo.problem        :as problem]
            [learngo.problems       :as problems]
            [learngo.ui-utils       :as ui]
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

(defn home-page []
  [:div
   [:h1 (i18n/translate :welcome-text)]
   [:button.btn.btn-primary.btn-lg
    {:type :button
     :on-click #(navigate :tutorial)}
    (i18n/translate :start-tutorial)
    " " [ui/glyphicon :play-circle]]])

(defn navbar [page-name & items]
  [:nav {:class "navbar navbar-default"}
   [:div.container-fluid
    [:div.navbar-header
     [:a.navbar-brand page-name]]
    [:ul {:class "nav navbar-nav"}
     (doall
      (for [[item icon] items]
        [:li {:key item
              :class (when (= @current-page item)
                       "active")
              :style {:cursor :pointer}}
         [:a {:on-click #(navigate item)}
          (when icon
            [ui/glyphicon icon])
          " "
          (i18n/translate item)]]))]]])

(defn problem-page []
  [problem/collection
   problems/all])

(defn history-page []
  [:div
   [:h1 (i18n/translate :go-history)]
   [:p (i18n/translate :lorem-ipsum)]])

(defn contribute-page []
  [:div
   [:h1 (i18n/translate :contribute)]
   [:a {:href "https://github.com/go-de/learn-go"}
    (i18n/translate :fork-us-on-github)]
   [:p (i18n/translate :or-build-and-send-a-problem)]
   [:h2 "Problem Editor"]
   [editor/make {:size 9
                 :title {:de "Vorschau"}
                 :text {:de "Problembeschreibung hier.."}}]])

(defn links-page []
  [:div
   [:h1 (i18n/translate :useful-links)]
   [:a {:href "http://www.dgob.de"} (i18n/translate :dgob)]])

(defn content []
  [:div
   [navbar (i18n/translate :learn-go)
    [:home :home]
    [:tutorial :education]
    [:history :globe]
    [:links :link]
    [:contribute :scissors]]
   [:div
    (case @current-page
      :home [home-page]
      :tutorial [problem-page]
      :history [history-page]
      :contribute [contribute-page]
      :links [links-page]
      [:h1 "Or else..."])]])
