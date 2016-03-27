(ns learngo.editor-test
  (:require [cljs.test      :refer-macros [is]]
            [devcards.core  :refer-macros [defcard-rg
                                           deftest
                                           start-devcard-ui!]]
            [learngo.editor :as editor]))


(defn example-editor-test []
  [editor/make {:size 9
                :title {:de "Problemtitel"}
                :text {:de "Problemtext"}}])

(defcard-rg example-editor
  [example-editor-test])
