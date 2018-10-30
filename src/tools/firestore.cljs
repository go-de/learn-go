(ns tools.firestore
  (:refer-clojure :exclude [get])
  (:require [cljs.core.async :as a]
            [clojure.string  :as str]
            [reagent.core    :as reagent]
            [taoensso.timbre :refer [info error]]))

(defrecord FirebaseDB [conn state])

(defn handle-auth-state-changes [db]
  (let [{:keys [state conn]} db
        handler (fn [user]
                  (swap! state assoc :current-user
                         (when user
                           {:email (.-email user)
                            :uid (.-uid user)
                            :display-name (.-displayName user)})))]
    (-> conn
        .auth
        (.onAuthStateChanged handler))))

(defn connect [config]
  (let [db-name (str (gensym 'db))
        conn (.initializeApp js/firebase config db-name)
        db (map->FirebaseDB {:conn conn
                             :state (reagent/atom {})})]
    (.firestore conn)
    (handle-auth-state-changes db)
    db))

(defn state [db]
  (-> db :state deref))

(defn current-user [db]
  (-> db state :current-user))

(defn promise->chan [promise]
  (let [ret (a/promise-chan)]
    (-> promise
        (.then (fn [docref]
                 (a/put! ret (or docref true))))
        (.catch (fn [err]
                  (a/put! ret err))))
    ret))

(defn auth [db]
  (-> db
      :conn
      .auth))

(defn sign-out [db]
  (-> db
      auth
      .signOut
      promise->chan))

(defn sign-in [db email pw]
  (-> db
      auth
      (.signInWithEmailAndPassword email pw)
      promise->chan))

(defn register [db email pw]
  (-> db
      auth
      (.createUserWithEmailAndPassword email pw)
      promise->chan))

(defn delete-current-user [db]
  (-> db
      auth
      .-currentUser
      .delete
      promise->chan))

(defn as-str [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defn collection [db-or-doc coll-name]
  (.collection db-or-doc (as-str coll-name)))

(defn doc [coll id]
  (.doc coll (as-str id)))

(defn data [doc]
  (js->clj (.data doc) :keywordize-keys true))

(defn get [doc]
  (a/go
    (when-let [ref (<! (-> doc .get promise->chan))]
      (if (.-exists ref)
        (data ref)
        nil))))

(defn with-doc [doc component]
  (reagent/with-let [state (reagent/atom {})
                     unsub (.onSnapshot
                            doc
                            (fn [snap]
                              (info "with-doc snap" (data snap))
                              (when snap
                                (reset! state (data snap)))))]
    [component @state]
    (finally (unsub))))

;; (defn delete! [doc]
;;   (info "delete!")
;;   (-> doc
;;       .delete
;;       promise->chan))

;; (defn add! [coll data]
;;   (info "add!")
;;   (-> coll
;;       (.add (clj->js data))
;;       promise->chan))

;; (defn replace! [doc data]
;;   (info "replace!")
;;   (-> doc
;;       (.set (clj->js data))
;;       promise->chan))

;; (defn merge! [doc data]
;;   (info "merge!")
;;   (-> doc
;;       (.set (clj->js data) #js{:merge true})
;;       promise->chan))

;; (defn update! [doc update-fn & update-args]
;;   (info "update!")
;;   (a/go
;;     (let [data (<! (get doc))]
;;       (<! (replace! doc (apply update-fn data update-args))))))

;; #_(defn ^:export run []
;;   (when-let [dom-node (.getElementById js/document "container")]
;;     (reagent/render sample-component dom-node)))


;; ;; (def root-user (user :root))

;; ;; (def problem-sets (collection db :problem-sets))
;; ;; (def problems (collection db :problems))
