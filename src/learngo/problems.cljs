(ns learngo.problems)

(def capture-1
  {:title {:de "Aller Anfang ist leicht"}
   :text {:de "Fange den weißen Stein"
          :en "Capture the white stone"}
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

(def capture-2
  {:stones
   {[3 4] :black,
    [4 6] :black,
    [5 5] :black,
    [4 5] :white,
    [5 4] :black,
    [3 5] :black,
    [4 4] :white},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars
   {[4 3] {:status :right, :vars {}},
    :any {:reply [4 3], :vars {}, :status :wrong}},
   :left 0,
   :title {:de "Nimm zwei"}
   :text {:de "Fange zwei Steine mit einem Zug"
          :en "Capture two stones in one move"}})

(def capture-3
  {:stones
   {[3 4] :white,
    [4 6] :black,
    [2 4] :black,
    [5 5] :white,
    [5 6] :black,
    [4 5] :white,
    [3 3] :black,
    [3 5] :white,
    [6 5] :black,
    [3 6] :black,
    [4 4] :black,
    [2 5] :black},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars {[5 4] {:status :right},
          :any {:reply [5 4], :vars {}, :status :wrong}},
   :left 0,
   :title {:de "Vier gewinnt"}
   :text {:de "Fange in einem Zug",
          :en "Capture in one move"}})

(def capture-4
  {:stones
   {[4 3] :black,
    [4 6] :black,
    [5 5] :black,
    [5 6] :white,
    [4 7] :black,
    [4 5] :white,
    [5 4] :black,
    [2 6] :white,
    [3 5] :black,
    [6 6] :white,
    [3 6] :white,
    [4 4] :white},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars
   {[3 4] {:status :right, :vars {}}, :any {:status :wrong, :vars {}}},
   :left 0,
   :title {:de "Die Zwei"}
   :text {:de "Fange zwei Steine in einem Zug"
          :en "Capture two stones in one move"}})

(def capture-5
  {:stones
   {[3 4] :black,
    [4 6] :white,
    [2 4] :white,
    [5 6] :white,
    [2 7] :white,
    [4 5] :black,
    [2 6] :white,
    [3 5] :white,
    [3 6] :black,
    [3 7] :black},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars
   {:any {:reply [2 5], :vars {}, :status :wrong},
    [2 5] {:status :right, :vars {}}},
   :left 0,
   :title {:de "Eine schlagende Verbindung"}
   :text {:de "Fange den weißen Stein und verbinde deine"
          :en "Capture the white stone and connect yours"}})

(def capture-side
  {:stones
   {[4 8] :white,
    [5 8] :black,
    [3 8] :white,
    [3 7] :black,
    [2 8] :black},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars
   {[5 7]
    {:reply [4 7],
     :vars
     {[4 6] {:status :right, :vars {}},
      :any {:reply [4 6], :vars {}, :status :wrong}},
     :status nil},
    [4 6] {:reply [4 7], :vars {[5 7] {}}, :status nil},
    [4 7] {:status :right, :vars {}},
    :any {:reply [5 7], :vars {}, :status :wrong}},
   :left 0,
   :title {:de "Randfang"}
   :text {:de "Fange zwei Steine am Rand"
          :en "Capture the two stones at the side"}})


(def capture-side2
  {:stones
   {[0 8] :white,
    [0 7] :white,
    [0 6] :black,
    [1 8] :white,
    [2 8] :black},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars
   {[1 7] {:status :right, :vars {}}, :any {:status :wrong, :vars {}}},
   :left 0,
   :title {:de "Tödliche Ecke"}
   :text {:de "Fange die weißen Steine mit einem Zug"
          :en "Capture the white stones in one move"}})

(def capture-side3
  {:stones
   {[5 7] :white,
    [4 6] :white,
    [5 6] :black,
    [4 7] :black,
    [2 7] :white,
    [7 7] :black,
    [6 6] :black,
    [6 7] :white,
    [3 6] :white,
    [3 7] :black,
    [2 5] :white,
    [7 6] :black},
   :bottom 0,
   :top 0,
   :size 9,
   :right 0,
   :vars
   {[6 8]
    {:reply [3 8],
     :vars
     {[5 8] {:status :right, :vars {}},
      :any {:reply [4 8], :vars {}, :status :wrong},
      [4 8] {:reply [5 8], :vars {}, :status :wrong}},
     :status nil},
    [5 8]
    {:reply [3 8],
     :vars
     {[6 8] {:status :right, :vars {}},
      :any {:reply [4 8], :vars {}, :status :wrong},
      [4 8] {:reply [6 8], :vars {}, :status :wrong}},
     :status nil}},
   :left 0,
   :title {:de "Wettlauf"}
   :text {:de "Fange die zwei weißen Steine am Rand (3 Züge)"
          :en "Capture the two white stones at the side (3 moves)"}})

(def geta-1
  {:title {:de "Im Netz der Spinne"}
   :text {:de "Fange den mit \"A\" markierten Stein"
          :en "Capture the stone marked \"A\""}
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
   capture-2
   capture-3
   capture-4
   capture-5
   capture-side
   capture-side2
   capture-side3
   geta-1])
