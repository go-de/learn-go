(ns learngo.problems)

(def capture-1-a
  {:title {:de "Schlagen"
           :en "Capturing"}
   :text {:de "Schlage den weißen Stein."}
   :stones {[3 4] :black
            [5 4] :black
            [4 5] :black
            [4 4] :white}
   :vars {[4 3] {:status :right
                 :text {:de "Gut gemacht!"}}
          :any  {:status :wrong
                 :text {:de "Probier's nochmal. Man schlägt Steine, indem man sie von allen vier Seiten umzingelt."}
                 :reply  [4 3]}}})

(def capture-1-b
  {:size 9,
   :stones
   {[4 3] :black,
    [3 4] :black,
    [3 2] :black,
    [2 4] :white,
    [3 3] :white},
   :player :white,
   :vars
   {[2 3] {:text {:de "Korrekt"}, :status :right},
    :any {:reply [2 3], :status :wrong, :text {:de "Nicht ganz"}}},
   :title {:de "Schlagen (2)"},
   :text {:de "Schlage einen weißen Stein"}})

(def capture-1-c
  {:size 9,
   :player :black,
   :stones {[7 2] :black, [8 3] :black, [8 2] :white},
   :vars
   {[8 1] {:status :right :text {:de "Richtig"}},
    :any {:status :wrong, :text {:de "Leider falsch"}}},
   :title {:de "Schlagen am Rand"},
   :text {:de "Schlage den weißen Stein"}})

(def capture-1-d
  {:size 9,
   :player :white,
   :stones {[5 7] :white, [4 7] :black, [4 8] :white, [5 8] :black},
   :title {:de "Schlagen am Rand (2)"},
   :text {:de "Schlage einen weißen Stein"},
   :vars
   {[3 8] {:status :right, :text {:de "Genau!"}},
    :any {:status :wrong, :text {:de "Falsch"}}}})

(def capture-1-e
  {:size 9,
   :player :white,
   :stones {[8 1] :black, [8 0] :white},
   :title {:de "Schlagen in der Ecke"},
   :text {:de "Schlage den Stein"},
   :vars
   {[7 0] {:text {:de "Richtig"}, :status :right},
    :any {:status :wrong, :text {:de "Probiere es nochmal"}}}})

(def capture-1-f
  {:size 9,
   :stones
   {[5 6] :black,
    [8 8] :white,
    [7 7] :white,
    [6 7] :black,
    [6 5] :white,
    [7 8] :black,
    [7 6] :white},
   :player :white,
   :title {:de "Schlagen in der Ecke (2)"},
   :text {:de "Schlage einen Stein"},
   :vars
   {[6 8] {:reply [8 7], :status :wrong, :text {:de "Nein"}},
    [8 7] {:text {:de "Ja, genau so"}, :status :right},
    :any
    {:reply [6 8],
     :text {:de "Nun kann weiß einen Stein schlagen"},
     :status :wrong}}})

(def capture-2
  {:stones
   {[3 4] :black,
    [4 6] :black,
    [5 5] :black,
    [4 5] :white,
    [5 4] :black,
    [3 5] :black,
    [4 4] :white},
   :vars
   {[4 3] {:status :right, :vars {}},
    :any {:reply [4 3], :vars {}, :status :wrong}},
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
   :vars {[5 4] {:status :right},
          :any {:reply [5 4], :vars {}, :status :wrong}},
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
   :vars
   {[3 4] {:status :right, :vars {}}, :any {:status :wrong, :vars {}}},
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
   :vars
   {:any {:reply [2 5], :vars {}, :status :wrong},
    [2 5] {:status :right, :vars {}}},
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
   :vars
   {[1 7] {:status :right, :vars {}}, :any {:status :wrong, :vars {}}},
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
   :title {:de "Wettlauf"}
   :text {:de "Fange die zwei weißen Steine am Rand (3 Züge)"
          :en "Capture the two white stones at the side (3 moves)"}})

(def race
  {:stones
   {[5 7] :white,
    [4 3] :white,
    [3 4] :black,
    [1 2] :white,
    [5 3] :white,
    [2 2] :black,
    [3 2] :black,
    [4 6] :white,
    [2 4] :black,
    [4 2] :black,
    [1 3] :white,
    [2 3] :black,
    [5 2] :white,
    [4 7] :black,
    [3 1] :white,
    [5 1] :black,
    [3 0] :black,
    [2 7] :white,
    [4 5] :white,
    [1 1] :black,
    [3 3] :white,
    [2 6] :white,
    [3 5] :black,
    [1 4] :white,
    [4 8] :white,
    [2 1] :white,
    [4 1] :white,
    [3 6] :black,
    [3 8] :white,
    [6 1] :black,
    [3 7] :black,
    [4 0] :black,
    [2 5] :white},
   :size 9,
   :title {:de "Unübersichtlich"},
   :player :black,
   :vars
   {[4 4] {:reply [5 4], :status :wrong},
    [2 0] {:status :right},
    :any {:reply [4 4], :status :wrong}},
   :text {:de "Schwarz muss sich befreien!"}})

(def race2
  {:size 9,
   :title {:de "Tödliche Umarmung"}
   :stones
   {[4 3] :white,
    [3 4] :white,
    [5 3] :black,
    [3 2] :white,
    [2 4] :black,
    [4 2] :black,
    [2 3] :black,
    [5 2] :white,
    [5 1] :white,
    [4 5] :black,
    [5 4] :black,
    [3 5] :black,
    [2 1] :white,
    [4 4] :white},
   :player :white,
   :vars
   {[3 1]
    {:reply [4 1],
     :vars
     {[2 2] {:status :wrong},
      [3 3] {:reply [4 2], :status :wrong},
      :any {:status :wrong, :reply [3 3]}}},
    [3 3] {:status :right},
    [4 1] {:reply [3 3], :status :wrong},
    :any {:reply [3 3], :status :wrong}}})

(def race3
  {:size 9,
   :player :black,
   :title {:de "Ins kurze Eck"}
   :stones
   {[3 4] :white,
    [4 6] :white,
    [0 7] :white,
    [4 7] :white,
    [2 7] :white,
    [0 6] :black,
    [2 6] :black,
    [1 4] :black,
    [1 7] :white,
    [1 8] :white,
    [3 6] :white,
    [3 8] :black,
    [1 6] :black,
    [3 7] :black,
    [2 8] :black},
   :vars
   {[0 8] {:status :right},
    [4 8] {:reply [5 8], :status :wrong},
    :any {:reply [4 8], :status :wrong}}})


(def geta-1
  {:title {:de "Im Netz der Spinne"}
   :text {:de "Fange den mit \"A\" markierten Stein"
          :en "Capture the stone marked \"A\""}
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

(def crane
  {:size 9,
   :title {:de "Kranichsnest"}
   :player :black,
   :stones
   {[2 4] :white,
    [7 2] :black,
    [5 6] :black,
    [2 3] :white,
    [5 2] :black,
    [7 4] :black,
    [6 3] :white,
    [6 6] :black,
    [6 5] :white,
    [7 5] :black,
    [4 4] :white,
    [7 3] :black,
    [6 2] :black,
    [6 4] :white,
    [7 6] :black},
   :vars
   {[5 4]
    {:reply [5 3],
     :vars
     {[4 3]
      {:reply [5 5],
       :vars
       {[3 4] {:reply [3 5], :status :wrong},
        [4 5]
        {:reply [5 4],
         :vars
         {[3 4] {:status :right}, :any {:reply [3 4], :status :wrong}}},
        :any {:reply [4 5], :status :wrong}}},
      :any {:status :wrong, :reply [4 3]}}},
    :any {:reply [5 4], :status :wrong}}})

(def ladder
  {:size 9,
   :stones {[3 4] :black, [4 5] :black, [3 3] :black, [4 4] :white},
   :player :black,
   :vars
   {[5 4]
    {:reply [4 3],
     :vars
     {[4 2]
      {:reply [5 3],
       :vars
       {[6 3]
        {:reply [5 2],
         :vars
         {[5 1]
          {:reply [6 2],
           :vars
           {[7 2]
            {:reply [6 1],
             :vars
             {[7 1]
              {:reply [6 0],
               :vars
               {[7 0]
                {:reply [5 0],
                 :vars
                 {[4 0] {:status :right},
                  [4 1] {:status :wrong},
                  :any {:reply [4 1], :status :wrong}}},
                [5 0]
                {:reply [6 4],
                 :vars
                 {[5 5] {:reply [7 3], :status :wrong},
                  [7 0] {:status :right},
                  :any {:reply [5 5], :status :wrong}}}}},
              [6 0]
              {:reply [7 1],
               :vars
               {[8 1] {:reply [7 0], :vars {[8 0] {:status :right}}},
                [7 0]
                {:reply [8 1],
                 :vars
                 {[8 2]
                  {:reply [4 1],
                   :vars
                   {[3 2] {:reply [5 0], :status :wrong},
                    [8 0] {:status :right},
                    :any {:reply [3 2], :status :wrong}}},
                  [8 0] {:reply [5 0], :status :wrong}}}}}}},
            :any {:reply [7 2], :status :wrong}}},
          :any {:reply [5 1], :status :wrong}}},
        :any {:reply [6 3], :status :wrong}}},
      :any {:reply [4 2], :status :wrong}}},
    :any {:reply [5 4], :status :wrong}},
   :title {:de "Treppe"},
   :text {:de "Fange weiß - Achtung viele Züge!"}})

(def yose-hane
  {:size 9,
   :title {:de "Endspiel"}
   :text {:de "Finde die beste Zugfolge"}
   :stones
   {[4 3] :white,
    [3 4] :black,
    [5 3] :black,
    [3 2] :white,
    [4 6] :black,
    [2 4] :white,
    [4 2] :black,
    [5 5] :black,
    [5 6] :black,
    [5 2] :black,
    [4 7] :black,
    [3 1] :white,
    [5 1] :black,
    [2 7] :white,
    [4 5] :white,
    [3 3] :white,
    [5 4] :black,
    [2 6] :white,
    [6 3] :black,
    [3 5] :white,
    [4 8] :white,
    [4 1] :white,
    [3 6] :black,
    [5 8] :black,
    [3 8] :white,
    [4 4] :black,
    [3 7] :white,
    [2 5] :white},
   :player :black,
   :vars
   {[2 2] {:reply [2 3], :status :wrong},
    [2 3] {:reply [2 2], :vars {[1 3] {:reply [1 2], :status :wrong}}},
    [6 0] {:reply [4 0], :status :wrong},
    [5 0] {:reply [4 0], :status :wrong},
    [4 0]
    {:reply [3 0],
     :vars
     {[2 3]
      {:reply [2 2],
       :vars {[1 3] {:reply [1 2], :vars {}, :status :wrong}}},
      [5 0] {:status :right},
      :any {:reply [5 0], :status :wrong}}},
    :any {:reply [5 0], :status :wrong}}})

(def all
  [capture-1-a
   capture-1-b
   capture-1-c
   capture-1-d
   capture-1-e
   capture-1-f
   capture-2
   capture-3
   capture-4
   capture-5
   capture-side
   capture-side2
   capture-side3
   race
   race2
   race3
   geta-1
   crane
   ladder
   yose-hane])
