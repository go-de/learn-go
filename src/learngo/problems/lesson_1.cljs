(ns learngo.problems.lesson-1)

(def data
  {:title {:de "Steine schlagen"}
   :problems
   [{:title {:de "Schlagen"
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
                   :reply  [4 3]}}}

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
     :text {:de "Schlage einen weißen Stein"}}

    {:size 9,
     :player :black,
     :stones {[7 2] :black, [8 3] :black, [8 2] :white},
     :vars
     {[8 1] {:status :right :text {:de "Richtig"}},
      :any {:status :wrong, :text {:de "Leider falsch"}}},
     :title {:de "Schlagen am Rand"},
     :text {:de "Schlage den weißen Stein"}}

    {:size 9,
     :player :white,
     :stones {[5 7] :white, [4 7] :black, [4 8] :white, [5 8] :black},
     :title {:de "Schlagen am Rand (2)"},
     :text {:de "Schlage einen weißen Stein"},
     :vars
     {[3 8] {:status :right, :text {:de "Genau!"}},
      :any {:status :wrong, :text {:de "Falsch"}}}}

    {:size 9,
     :player :white,
     :stones {[8 1] :black, [8 0] :white},
     :title {:de "Schlagen in der Ecke"},
     :text {:de "Schlage den Stein"},
     :vars
     {[7 0] {:text {:de "Richtig"}, :status :right},
      :any {:status :wrong, :text {:de "Probiere es nochmal"}}}}

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
       :status :wrong}}}

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
     :title {:de "Mehrere Steine schlagen"}
     :text {:de "Fange zwei Steine mit einem Zug"
            :en "Capture two stones in one move"}}

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
     :title {:de "Mehrere Steine schlagen (2)"}
     :text {:de "Schlage vier Steine",
            :en "Capture four stones in one move"}}

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
     :title {:de "Mehrere Steine schlagen (3)"}
     :text {:de "Schlage zwei Steine"
            :en "Capture two stones in one move"}}

    ]})
