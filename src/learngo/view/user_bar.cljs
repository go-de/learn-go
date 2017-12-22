(ns learngo.view.user-bar
  (:require [learngo.db.auth :as auth]))

(defn sign-in-button []
  [:button.btn.btn-primary {:on-click auth/sign-in}
   "Sign In"])

(defn sign-out-button []
  [:button.btn.btn-warning {:on-click auth/sign-out}
   "Sign Out"])

#_(defn delete-account-button []
  [:button.btn.btn-danger {:on-click auth-db/delete-account}
   "Delete Account"])

(defn user-bar []
  [:p.user-bar
   (if-let [{:keys [display-name]} @auth/current-user]
     [:span display-name " "
      [auth/admin-only "(Admin) "]
      [auth/editor-only "(Editor) "]
      [sign-out-button]]
     [sign-in-button])])
