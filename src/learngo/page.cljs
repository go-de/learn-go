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

(defroute contact "/contact" []
  (reset! current-page :contact))

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
  [:div {:class "content"}
   [:h1 (i18n/translate :welcome-text)]
   [:div {:class "row"}
     [:div {:class "col-sm-6 justify"}
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla non ex lacinia, euismod turpis vitae, placerat ante. Aenean faucibus diam risus, imperdiet feugiat justo dapibus eget. Vivamus iaculis metus lacus, mollis placerat nisl gravida sed. Nulla facilisi. Sed hendrerit mi porttitor, pharetra leo a, ultricies justo. Mauris quis quam sit amet nibh vehicula accumsan quis eu libero. Duis egestas ipsum eu eros gravida consectetur. Cras molestie erat in metus laoreet aliquam. Nunc blandit ligula nec risus rutrum fermentum. Aenean vitae iaculis turpis."
     ]
     [:div {:class "col-sm-4"}
       [:button.btn.btn-default.btn-block
        {:type :button
         :on-click #(navigate :tutorial)}
        [:span {:class "btn-header"}(i18n/translate :start-tutorial)]
        [:span {:class "btn-subtext"} (i18n/translate :start-tutorial-subtext)]
       ]
       [:button.btn.btn-default.btn-block
         {:type :button
          :on-click #(navigate :history)}
         [:span {:class "btn-header"}(i18n/translate :start-history)]
         [:span {:class "btn-subtext"} (i18n/translate :start-history-subtext)]
       ]
     ]
   ]

  ])

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
  [:div {:class "content"}
  [problem/collection
   problems/all]])

(defn history-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :go-history)]
   [:p {:class "justify"} (i18n/translate :lorem-ipsum)]])

(defn contribute-page []
  [:div
   [:h1 (i18n/translate :contribute)]
   [:ul
    [:li
     [:a {:href "https://github.com/go-de/learn-go"}
      (i18n/translate :fork-us-on-github)]]
    [:li
     [:p (i18n/translate :or-build-and-send-a-problem) ":"]]]
   [:h3 (i18n/translate :problem-editor)]
   [editor/make {:size 9
                 :title {:de "Problemtitel ..."}}]])

(defn links-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :useful-links)]
   [:a {:href "http://www.dgob.de"} (i18n/translate :dgob)]])

(defn contact-page []
  [:div {:class "content"}
   [:h1 (i18n/translate :contact-us)]])

(defn content []
  [:div
   [navbar (i18n/translate :learn-go)
    [:home :home]
    [:tutorial :education]
    [:history :globe]
    [:links :link]
    [:contribute :scissors]
    [:contact :envelope]]
   [:div
    (case @current-page
      :home [home-page]
      :tutorial [problem-page]
      :history [history-page]
      :contribute [contribute-page]
      :links [links-page]
      :contact [contact-page]
      [:h1 "Page not found"])]])
