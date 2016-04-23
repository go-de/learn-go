(ns learngo.cards
  (:require [learngo.logic.board-test]
            [learngo.logic.editor-test]
            [learngo.logic.problem-test]
            [learngo.logic.sgf-test]
            [learngo.i18n-test]
            [learngo.view.board-test]
            [learngo.view.editor-test]
            [learngo.view.intro-test]
            [learngo.view.layout-test]
            [learngo.view.problem-test]
            [taoensso.timbre :as timbre]))

(timbre/set-level! :debug)

;; [learngo.buttons-test]
;; [learngo.editor-test]
;; [learngo.forms-test]
;;
;; [learngo.page-test]
