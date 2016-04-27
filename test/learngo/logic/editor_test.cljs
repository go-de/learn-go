(ns learngo.logic.editor-test
  (:require [cljs.test             :refer-macros [is]]
            [devcards.core         :refer-macros [defcard-rg deftest]]
            [learngo.logic.editor  :as editor]
            [learngo.logic.problem :as problem]
            [reagent.core          :as r]))

(deftest get-var
  (is (= {:foo 1} (editor/get-var {:foo 1})))
  (is (= {:baz :bam}
         (editor/get-var
          {:vars {[1 1] {:baz :bam}}
           :path [[1 1]]}))))

(deftest update-var
  (is (= {:foo 1}
         (editor/update-var {} assoc :foo 1)))
  (is (= {:vars {[1 1] {:foo 1}}
          :path [[1 1]]}
         (editor/update-var {:path [[1 1]]} assoc :foo 1))))

(deftest assoc-var
  (is (= {:vars {[1 1] {:foo 1
                        :bar 2}}
          :path [[1 1]]}
         (editor/assoc-var
          {:vars {[1 1] {:foo 1}}
           :path [[1 1]]}
          :bar 2))))

(deftest merge-var
  (is (= {:vars {[1 1] {:foo 1
                        :bar 2}}
          :path [[1 1]]}
         (editor/merge-var
          {:vars {[1 1] {:foo 1
                         :bar 42}}
           :path [[1 1]]}
          {:bar 2}))))

(deftest play-black
  (is (= {:path [[3 4]]
          :vars {[3 4] {}}}
         (editor/play-black {} [3 4])))
  (is (= {:path [[3 4] [5 5]]
          :vars {[3 4] {:vars {[5 5] {}}}}}
         (editor/play-black
          {:path [[3 4]]
           :vars {[3 4] {}}}
          [5 5]))))

(deftest play-white-first-time
  (is (= {:path [[3 4]]
          :vars {[3 4] {:reply [5 5]}}}
         (editor/play-white
          {:path [[3 4]]
           :vars {[3 4] {}}}
          [5 5]))))

(deftest play-white-again
  (is (= {:path [[3 4]]
          :vars {[3 4] {:reply [4 5]
                        :vars {:any :foo}}}}
         (editor/play-white
          {:path [[3 4]]
           :vars {[3 4] {:reply [4 5]
                         :vars {:any :foo}}}}
          [4 5]))))

(deftest play-white-replace
  (is (= {:path [[3 4]]
          :vars {[3 4] {:reply [9 9]}}}
         (editor/play-white
          {:path [[3 4]]
           :vars {[3 4] {:reply [4 5]
                         :vars {:any :foo}}}}
          [9 9]))))

(deftest play
  (is (= {:vars {[1 1] {}}
          :path [[1 1]]
          :player :white}
         (-> {}
             (editor/play [1 1]))))
  (is (= {:vars {[1 1] {:reply [1 2]}}
          :path [[1 1]]
          :player :black}
         (-> {}
             (editor/play [1 1])
             (editor/play [1 2]))))
  (is (= {:vars {[1 1] {:reply [1 2]
                        :vars {[2 2] {}}}}
          :path [[1 1] [2 2]]
          :player :white}
         (-> {}
             (editor/play [1 1])
             (editor/play [1 2])
             (editor/play [2 2])))))

(deftest play-any
  (let [editor (-> {}
                   (editor/play :any)
                   (editor/play [1 2]))]
    (is (= {:vars {:any {:reply [1 2]}}
            :path [:any]
            :player :black}
           editor))
    (is (= {:size 9
            :stones {[1 2] :white}}
           (problem/current-board (assoc editor :size 9))))))

(deftest play-tool
  (let [editor {:size 9
                :vars {[1 1] {:reply [1 2]
                              :vars {[2 2] {}}}}
                :path [[1 1] [2 2]]
                :player :white}]
    (is (= editor
           (editor/play-tool editor [1 1])))
    (is (= editor
           (editor/play-tool editor [1 2])))
    (is (= editor
           (editor/play-tool editor [2 2])))
    (is (= {:size 9
            :vars {[1 1] {:reply [1 2]
                          :vars {[2 2] {:reply [3 3]}}}}
            :path [[1 1] [2 2]]
            :player :black}
           (editor/play-tool editor [3 3])))))

(deftest add-tool
  (is (= {:stones {[3 4] :white}}
         (-> {}
             (editor/add-tool :white [3 3])
             (editor/add-tool :white [3 4])
             (editor/add-tool :white [3 3]))))
  (is (= {:stones {[3 4] :white}}
         (-> {}
             (editor/add-tool :white [3 3])
             (editor/add-tool :white [3 4])
             (editor/add-tool :black [3 3]))))
  (is (= {:stones {[3 3] :black
                   [3 4] :white}}
         (-> {}
             (editor/add-tool :white [3 3])
             (editor/add-tool :white [3 4])
             (editor/add-tool :black [3 3])
             (editor/add-tool :black [3 3])))))

(deftest up
  (let [editor {:vars {[1 1] {:reply [1 2]}}
                :path [[1 1]]
                :player :black}
        up-1 (editor/up editor)
        up-2 (editor/up up-1)
        up-3 (editor/up up-2)]
    (is (= {:vars {[1 1] {:reply [1 2]}}
            :path [[1 1]]
            :player :white}
           up-1))
    (is (= {:vars {[1 1] {:reply [1 2]}}
            :path []
            :player :black}
           up-2))
    (is (= up-2
           up-3))))

(deftest delete-current-move
  (let [editor {:vars {[1 1] {:reply [1 2]
                              :vars {[2 2] {:reply [6 6]}}}}
                :path [[1 1] [2 2]]
                :player :white}
        del-1 (editor/delete-current-move editor)
        del-2 (editor/delete-current-move del-1)
        del-3 (editor/delete-current-move del-2)
        del-4 (editor/delete-current-move del-3)]
    (is (= {:vars {[1 1] {:reply [1 2]
                          :vars {}}}
            :path [[1 1]]
            :player :black}
           del-1))
    (is (= {:vars {[1 1] {:vars {}}}
            :path [[1 1]]
            :player :white}
           del-2))
    (is (= {:vars {}
            :path []
            :player :black}
           del-3))
    (is (= del-3
           del-4))))

(deftest refutes-all-others?
  (let [editor-1 {:vars {[1 1] {}
                         [2 1] {}
                         :any {:reply [1 1]
                               :status :wrong}}
                  :path [[1 1]]
                  :player :white}
        editor-2 (assoc editor-1 :path [])
        editor-3 (assoc editor-1 :player :black)
        editor-4 (assoc-in editor-1 [:vars :any :reply] [1 2])]
    (is (editor/refutes-all-others? editor-1))
    (is (not (editor/refutes-all-others? editor-2)))
    (is (not (editor/refutes-all-others? editor-3)))
    (is (not (editor/refutes-all-others? editor-4)))))

(deftest refute-all-others
  (let [editor {:vars {[1 1] {}}
                :path [[1 1]]
                :player :white}]
    (is (= {:vars {[1 1] {}
                   :any {:reply [1 1]
                         :status :wrong}}
            :path [[1 1]]
            :player :white}
           (editor/refute-all-others editor)))))
