(ns tools.firestore-test
  (:require [cljs.test         :refer [async is]]
            [cljs.core.async   :refer [go <!]]
            [devcards.core     :refer-macros [deftest defcard-rg]]
            [learngo.db.config :refer [config]]
            [tools.async-test  :refer [deftest-go]]
            [tools.firestore   :as db]))

(set! devcards.core/test-timeout 5000)

(defonce db (db/connect config))

(def test-email "tmpuser@go-lernen.de")
(def test-pw "testUser")

(deftest-go register
  (let [e (<! (db/sign-in db test-email test-pw))]
    (is (instance? js/Error e))
    (<! (db/register db test-email test-pw))
    (is (= (:email (db/current-user db))
           test-email))))

(deftest-go sign-out
  (is (db/current-user db))
  (<! (db/sign-out db))
  (is (nil? (db/current-user db))))

(deftest-go sign-in
  (is (nil? (db/current-user db)))
  (<! (db/sign-in db test-email test-pw))
  (is (= (:email (db/current-user db))
         test-email)))

(deftest-go delete-current-user
  (<! (db/delete-current-user db))
  (is (nil? (db/current-user db))))

(defn list-view [coll]
  )

(defn doc-view [doc]
  )

(defcard-rg listen
  [db/listen db ])
