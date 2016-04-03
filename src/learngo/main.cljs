(ns learngo.main
  (:require [learngo.view.editor  :as editor-view]
            [learngo.view.page    :as page-view]
            [learngo.view.problem :as problem-view]
            [learngo.problems     :as problems]
            [reagent.core         :as r]))

(enable-console-print!)

(defn problems-component []
  [problem-view/collection
   problems/all])

(defn ^:export problems []
  (when-let [node (.getElementById js/document "container")]
    (r/render problems-component node)))

(defn editor-component []
  [editor-view/editor {:size 9
                       :text "Problem description comes here..."}])

(defn ^:export editor []
  (when-let [node (.getElementById js/document "container")]
    (r/render editor-component node)))

(defn page-component []
  [page-view/content])

(defn ^:export learngo []
  (when-let [node (.getElementById js/document "container")]
    (r/render page-component node)))
