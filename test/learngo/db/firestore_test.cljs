(ns learngo.db.firestore-test
  (:require [learngo.db.firestore :as db]
            [cljs.core.async :as a]
            [cljs.test :refer [is async]]
            [devcards.core :refer-macros [defcard-rg deftest]]))

(set! devcards.core/test-timeout 2000)

;; (deftest add-user
;;   (async done
;;     (a/go
;;       (let [ref (<! (db/add-user! {:name "ME"}))]
;;         (is (.-id ref))
;;         (is (= (<! (db/get ref))
;;                {:name "ME"}))
;;         (is (<! (db/delete! ref))))
;;       (done))))

;; (deftest set-user
;;   (async done
;;     (a/go
;;       (let [root (db/user :root)
;;             res (<! (db/replace! root {:name "ME"}))])
;;       (done))))

;; (deftest get-root
;;   (async done
;;     (a/go
;;       (let [root (<! (db/get (db/user :root)))]
;;         (is (= root {:name "ME"})))
;;       (done))))

;; (defcard-rg users
;;   [db/users-ui])
