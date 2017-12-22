(ns learngo.db.firestore
  (:refer-clojure :exclude [get])
  (:require [cljs.core.async :as a]
            [clojure.string :as str]
            [reagent.core :as reagent]
            [taoensso.timbre :refer [info]]))

(def config
  #js {:apiKey "AIzaSyAvCoeVbJrAbhzV9mEXUh_fo35za0NNcz0"
       :authDomain "learn-go-d865b.firebaseapp.com"
       :databaseURL "https://learn-go-d865b.firebaseio.com"
       :projectId "learn-go-d865b"
       :storageBucket "learn-go-d865b.appspot.com"
       :messagingSenderId "286101574671"})

(defonce firebase js/firebase)

(defonce db
  (do
    (.initializeApp firebase config)
    (.firestore firebase)))

(def log js/console.info)

(defn promise->chan [promise]
  (let [ret (a/promise-chan)]
    (-> promise
        (.then (fn [docref]
                 (a/put! ret (or docref true))))
        (.catch (fn [err]
                  (js/console.error err)
                  (a/close! ret))))
    ret))

(defn as-str [x]
  (if (keyword? x)
    (name x)
    (str x)))

(defn collection [db-or-doc coll-name]
  (info "collection" coll-name)
  (.collection db-or-doc (as-str coll-name)))

(defn doc [coll id]
  (info "doc" id)
  (.doc coll (as-str id)))

(defn data [doc]
  (js->clj (.data doc) :keywordize-keys true))

(defn get [doc]
  (info "get")
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

(defn delete! [doc]
  (info "delete!")
  (-> doc
      .delete
      promise->chan))

(defn add! [coll data]
  (info "add!")
  (-> coll
      (.add (clj->js data))
      promise->chan))

(defn replace! [doc data]
  (info "replace!")
  (-> doc
      (.set (clj->js data))
      promise->chan))

(defn merge! [doc data]
  (info "merge!")
  (-> doc
      (.set (clj->js data) #js{:merge true})
      promise->chan))

(defn update! [doc update-fn & update-args]
  (info "update!")
  (a/go
    (let [data (<! (get doc))]
      (<! (replace! doc (apply update-fn data update-args))))))

#_(defn ^:export run []
  (when-let [dom-node (.getElementById js/document "container")]
    (reagent/render sample-component dom-node)))


;; (def root-user (user :root))

;; (def problem-sets (collection db :problem-sets))
;; (def problems (collection db :problems))
