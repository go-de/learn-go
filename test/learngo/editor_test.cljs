(ns learngo.editor-test
  (:require [cljs.test      :refer-macros [is]]
            [devcards.core  :refer-macros [defcard-rg
                                           deftest
                                           start-devcard-ui!]]
            [learngo.editor :as editor]))


(defn example-editor-test []
  [editor/make {:size 9
                :text "Problem description comes here..."}])

(defcard-rg example-editor
  [example-editor-test])
