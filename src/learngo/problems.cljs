(ns learngo.problems)

(def capture-1
  {:text "Capture the white stone"
   :size 9
   :top 1
   :left 1
   :bottom 1
   :right 1
   :stones {[3 4] :black
            [5 4] :black
            [4 5] :black
            [4 4] :white}
   :vars {[4 3] {:status :right}
          :any  {:status :wrong
                 :reply  [4 3]}}})

(def geta-1
  {:text "Capture the stone marked \"A\""
   :size 9
   :top 1
   :left 1
   :bottom 1
   :right 1
   :marks {[4 4] "A"}
   :stones
   {[3 4] :black,
    [5 5] :black,
    [4 5] :black,
    [3 3] :black,
    [4 4] :white,
    [6 2] :white},
   :vars
   {[4 3]
    {:reply [5 4],
     :vars
     {[6 4] {:reply [5 3], :vars {}, :status :wrong},
      :any {:reply [6 4], :vars {}, :status :wrong}},
     :status nil},
    [5 3]
    {:reply [4 3],
     :vars
     {[4 2]
      {:reply [5 4],
       :vars
       {[6 4] {:status :right, :vars {}},
        :any {:reply [6 4], :vars {}, :status :wrong}},
       :status nil},
      :any {:status :wrong, :vars {}, :reply [4 2]}},
     :status nil},
    [5 4]
    {:reply [4 3],
     :vars
     {[4 2] {:reply [5 3], :vars {}, :status :wrong},
      :any {:reply [4 2], :vars {}, :status :wrong}},
     :status nil},
    :any {:status :wrong, :vars {}}}})

(def all
  [capture-1
   geta-1])
