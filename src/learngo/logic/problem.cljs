(ns learngo.logic.problem
  (:require [learngo.logic.board :as board]
            [learngo.utils       :refer [conjv]]
            [taoensso.timbre     :refer-macros [debug spy]]))

(defn point-from-shorthand [kw]
  (let [[fst & rst] (-> kw name)
        x (- (.charCodeAt fst 0)
             (.charCodeAt "a" 0))
        y (->> rst (apply str) int dec)]
    [x y]))

(defn stones-from-shorthand [board]
  (into {}
        (for [[color positions] board
              pt positions]
          [(point-from-shorthand pt) color])))

(defn point-to-shorthand [[x y]]
  (keyword (str
            (char (+ x (.charCodeAt "a" 0)))
            (inc y))))

(defn child-node [tree move]
  (or (get-in tree [:vars move])
      (get-in tree [:vars :any])))

(defn path-node [problem path]
  (reduce child-node problem path))

(defn current-node [problem]
  (path-node problem
             (:path problem)))

(defn current-moves [problem]
  (let [path (:path problem)
        paths (rest (reductions conj [] path))
        black-moves (for [move path]
                      (vector :black move))
        white-moves (for [path paths]
                      (->> path
                           (path-node problem)
                           :reply
                           (vector :white)))
        moves (interleave black-moves white-moves)
        moves (remove (fn [[_ move]]
                        (= move :any))
                      moves)]
    (spy moves)
    (if (and (not (:hide-feedback? problem))
             (second (last white-moves)))
      moves
      (butlast moves))))

(defn current-board [problem]
  (if (seq (:path problem))
    (reduce (fn [bd [color move]]
              (let [res (board/play bd color move)]
                res))
            (select-keys problem [:stones :size])
            (current-moves problem))
    problem))

(defn play-and-disable [problem move]
  (when-not (or (:disabled? problem)
                (not (child-node (current-node problem)
                                 move)))
    (when (board/play (current-board problem)
                      :black move)

      (-> problem
          (update :path conjv move)
          (assoc :disabled? true)
          (assoc :hide-feedback? true)))))

(defn show-feedback-and-enable [problem]
  (assoc problem
         :disabled? false
         :hide-feedback? false))
