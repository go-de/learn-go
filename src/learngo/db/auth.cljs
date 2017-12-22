(ns learngo.db.auth
  (:require [reagent.core :as reagent]
            [learngo.db.firestore :as db]
            [learngo.db.user :as user-db]
            [taoensso.timbre :refer [info]]))

(defn auth []
  (.auth js/firebase))

(defn sign-out []
  (.signOut (auth)))

(defn sign-in []
  (js/window.open "login_widget.html"
                  "Sign In"
                  "width=985,height=735"))

(defonce current-user
  (let [current-user (reagent/atom nil)]
    (js/firebaseui.auth.AuthUI. (js/firebase.auth))
    (-> (auth)
        (.onAuthStateChanged (fn [user]
                               (reset! current-user
                                       (when user
                                         {:email (.-email user)
                                          :uid (.-uid user)
                                          :display-name (.-displayName user)}))
                               (when user
                                 (user-db/assure-user-entry! @current-user)))))
    current-user))

(defn admin? []
  (= (:uid @current-user)
     "m4SvWW7xhJafjKYx3Si6mAkGb602"))

(defn admin-only [component]
  [:span
   (when (admin?)
     component)])

(defn editor-only [component]
  (let [inner (fn [roles]
                [:span
                 (when (:editor roles)
                   component)])]
    [:span
     (when-let [{:keys [uid]} @current-user]
       [db/with-doc
        (db/doc user-db/roles uid)
        inner])]))

#_(defn delete-account []
  (-> (auth)
      .-currentUser
      .delete
      (.catch (fn [error]
                (let [msg "Please sign in again to delete your account"]
                  (when (= (.-code error)
                           'auth/requires-recent-login')
                    (-> (sign-out)
                        (.then (fn []
                                 (js/setTimeout (fn []
                                                  (js/alert msg))
                                                1))))))))))
