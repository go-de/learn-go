(ns learngo.problem-test)

#_
(def capture
  (p/slides
   [{:text "This white stone can be captured."
     :board {:black [:d5 :f5 :e4]
             :white [:e5]}}
    {:text "It has only one free neighbour at A."
     :diff {"A" [:e6]}}
    {:text "If black plays on it..."
     :diff {:circle [:e6]
            :black [:e6]}}
    {:text "... it is gone!"
     :diff {:clear [:e5]}}]))

#_
(def capture-prob-1
  (p/problem
   {:text "Capture the white stone"
    :board {:black [:d5 :f5 :e4]
            :white [:e5]}
    :variations '([:e6 :right]
                  [:* :e6 :wrong])}))
