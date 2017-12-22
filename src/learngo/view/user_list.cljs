(ns learngo.view.user-list
  (:require [learngo.db.firestore :as db]
            [learngo.db.auth :as auth]
            [learngo.db.user :as user-db]
            [taoensso.timbre :refer [info]]))

(defn user-list-contents [state]
  [:div
   [:h2 "Users"]
   [:table.table
    [:thead
     [:tr
      [:th "id"]
      [:th "Name"]
      [:th "Email"]
      [:th "Editor?"]]]
    [:tbody
     (for [[id {:keys [display-name email roles]}] state]
       (let [editor? (boolean (get roles :editor))]
         ^{:key id}
         [:tr
          [:td id]
          [:td display-name]
          [:td email]
          [:td [:input {:type :checkbox
                        :checked editor?
                        :on-change (fn [e]
                                     (user-db/set-role! id :editor
                                                        (-> e
                                                            .-target
                                                            .-checked)))}]]]))]]])

(defn user-list []
  [auth/admin-only
   [user-db/with-users user-list-contents]])
