(ns learngo.db.user
  (:require [cljs.core.async :refer [go]]
            [learngo.db.firestore :as db :refer [db]]
            [reagent.core :as reagent]
            [taoensso.timbre :refer [info]]))

(def users (db/collection db :users))
(def roles (db/collection db :roles))

(defn user-doc [uid]
  (db/doc users uid))

(defn roles-doc [uid]
  (db/doc roles uid))

(defn set-role! [uid role has-role?]
  (db/update! (roles-doc uid) assoc role has-role?))

(defn with-users [component]
  (reagent/with-let [state (reagent/atom {})
                     unsub-users (.onSnapshot
                                  users
                                  (fn [snap]


                                    (.forEach snap
                                              (fn [doc]
                                                (swap! state update (.-id doc)
                                                       merge (db/data doc))))))

                     unsub-roles (.onSnapshot
                                  roles (fn [snap]
                                          (.forEach snap (fn [doc]
                                                           (swap!
                                                            state
                                                            assoc-in
                                                            [(.-id doc)
                                                             :roles]
                                                            (db/data doc))))))]
    [component @state]
    (finally
      (unsub-users)
      (unsub-roles))))

(defn assure-user-entry! [user]
  (go
    (info "assure-user-entry!" (:uid user))
    (let [doc (user-doc (:uid user))
          user-entry (<! (db/get doc))]
        (when-not user-entry
          (db/merge! doc (dissoc user :uid))))))
