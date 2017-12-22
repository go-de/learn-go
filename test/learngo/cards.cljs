(ns learngo.cards
  (:require [learngo.db.firestore-test]
            [learngo.db.user-test]
            [learngo.logic.board-test]
            [learngo.logic.editor-test]
            [learngo.logic.problem-test]
            [learngo.logic.sgf-test]
            [learngo.i18n-test]
            [learngo.view.board-test]
            [learngo.view.editor-test]
            [learngo.view.forms-test]
            [learngo.view.intro-test]
            [learngo.view.layout-test]
            [learngo.view.page-test]
            [learngo.view.problem-test]
            [learngo.view.user-bar-test]
            [learngo.view.user-list-test]
            [taoensso.timbre :as timbre]))

(timbre/set-level! :debug)
