(ns learngo.logic.editor
  (:require [learngo.logic.board   :as board]
            [learngo.logic.problem :as problem]
            [learngo.utils         :refer [conjv]]))

(defn var-path [path]
  (interleave (repeat :vars)
              path))

(defn current-var-path [editor]
  (var-path (:path editor)))

(defn get-var [editor]
  (get-in editor (current-var-path editor)))

(defn update-var [editor f & args]
  (if-let [path (seq (:path editor))]
    (apply update-in editor (var-path path) f args)
    (apply f editor args)))

(defn assoc-var [editor & args]
  (apply update-var editor assoc args))

(defn dissoc-var [editor & args]
  (apply update-var editor dissoc args))

(defn merge-var [editor m]
  (update-var editor merge m))

(defn play-black [editor pos]
  (-> editor
      (dissoc-var :status)
      (update :path conjv pos)
      (merge-var {})))

(defn play-white [editor pos]
  (if (-> editor
          get-var
          :reply
          (= pos))
    editor
    (-> editor
        (assoc-var :reply pos)
        (dissoc-var :vars))))

(defn play [editor pos]
  (let [{:keys [player]} editor]
    (if (= player :white)
      (-> editor
          (play-white pos)
          (assoc :player :black))
      (-> editor
          (play-black pos)
          (assoc :player :white)))))

(defn play-any [editor]
  (play editor :any))

(defn play-tool [editor pos]
  (let [bd (problem/current-board editor)
        playable? (board/play bd (:player editor) pos)]
    (if (and playable?
             (not (:disabled? editor)))
      (play editor pos)
      editor)))

(defn add-tool [editor color pos]
  (update editor :stones
          (fn [m]
            (if (get m pos)
              (dissoc m pos)
              (assoc m pos color)))))

(defn apply-tool [editor pos]
  (case (:tool editor)
    :play (play-tool editor pos)
    :add-black (add-tool editor :black pos)
    :add-white (add-tool editor :white pos)))

(defn up [editor]
  (let [{:keys [player path]} editor]
    (if (seq path)
      (if (= player :white)
        (-> editor
            (update :path pop)
            (assoc :player :black))
        (assoc editor :player :white))
      editor)))

(defn top [editor]
  (assoc editor
         :path []
         :player :black))

(defn select-tool [editor tool]
  (-> editor
      top
      (assoc :tool tool)))

(defn status [editor]
  (:status (get-var editor)))

(defn set-status [editor status]
  (assoc-var editor :status status))

(defn delete-current-move [editor]
  (if-let [last-black-move (last (:path editor))]
    (if (= (:player editor) :white)
      (-> editor
          up
          (update-var update :vars dissoc last-black-move))
      (-> editor
          up
          (dissoc-var :reply)))
    editor))

(defn ->problem [editor]
  (dissoc editor :path :tool))
