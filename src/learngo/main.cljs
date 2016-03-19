(ns learngo.main
  (:require [learngo.editor   :as editor]
            [learngo.problem  :as problem]
            [learngo.problems :as problems]
            [reagent.core     :as r]))

(defn problems-component []
  [problem/collection
   problems/all])

(defn ^:export problems []
  (when-let [node (.getElementById js/document "container")]
    (r/render problems-component node)))

(defn editor-component []
  [editor/make {:size 9
                :text "Problem description comes here..."}])

(defn ^:export editor []
  (when-let [node (.getElementById js/document "container")]
    (r/render editor-component node)))
