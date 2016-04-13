(ns learngo.logic.board)

(def other-color {:black :white
                  :white :black})

(defn coords [size]
  (range size))

(def points
  (memoize (fn [size]
             (apply sorted-set
                    (for [x (coords size)
                          y (coords size)]
                      [x y])))))

(def neighbours
  (memoize (fn [size [x y]]
             (set
              (filter (points size)
                      [[(dec x) y]
                       [(inc x) y]
                       [x (dec y)]
                       [x (inc y)]])))))


(defn put
  "A board with stones added at points. For removing stones, use dissoc."
  [board color & points]
  (update board :stones merge
          (zipmap points (repeat color))))

(defn make-board
  "Creates a go board of a given size.
  Can be given lists of initial black and white stones."
  [size & {:keys [black white]}]
  (let [put-fn #(apply put %1 %2 %3)]
    (-> {:size size}
        (put-fn :black black)
        (put-fn :white white))))

(def board-points (comp points :size))

(defn board-stones [board]
  (keys (:stones board)))

(defn neighb-fn [board]
  #(neighbours (:size board) %))

(defn of-col?-fn [board color]
  #(= (get-in board [:stones %]) color))

(defn chain
  "The set of all points connected to the chain at point"
  [board point]
  (let [color (get-in board [:stones point])
        neighbs (neighb-fn board)
        right-color? (of-col?-fn board color)]
    (loop [current #{point}
           chain #{}
           visited #{}]
      (if (empty? current)
        chain
        (let [current-with-right-color (filter right-color?
                                               current)]
          (recur (->> current-with-right-color
                      (mapcat neighbs)
                      (remove visited)
                      set)
                 (->> current-with-right-color
                      (into chain))
                 (into visited current)))))))

(defn liberties
  "The set of liberties of the chain at point"
  [board point]
  (->> (chain board point)
       (mapcat (neighb-fn board))
       (filter (of-col?-fn board nil))
       set))

(defn captured?
  "Is the chain at point captured?"
  [board point]
  (empty? (liberties board point)))

(defn one-element? [seq]
  (if (counted? seq)
    (= (count seq) 1)
    (and (first seq)
         (not (next seq)))))

(defn atari?
  [board point]
  (one-element? (liberties board point)))

(defn play
  "Gives the board after a move at point.
  Nil is returned when
    - point is not on board
    - point is occupied
    - move is illegal.
  Checks for simple ko rule."
  [board color point]
  (let [opp              (other-color color)
        size             (:size board)
        new-board        (put board color point)
        opp-neighbs      (filter (of-col?-fn board opp)
                                 (neighbours size point))
        captured-neighbs (filter #(captured? new-board %) opp-neighbs)
        suicide?         (and (empty? captured-neighbs)
                              (captured? new-board point))
        ko               (when (one-element? captured-neighbs)
                           (let [captured-chain
                                 (chain board
                                        (first captured-neighbs))]
                             (when (one-element? captured-chain)
                               (first captured-chain))))
        new-board        (if ko
                           (assoc new-board :ko ko)
                           (dissoc new-board :ko))]
    (when-not size
      (throw (js/Error. "cannot play on board without size")))
    (when (and ((points size) point)
               (not (get-in board [:stones point]))
               (not (and ko
                         (= (:ko board)
                            point)))
               (not suicide?))
      (apply update new-board :stones dissoc
             (mapcat #(chain board %)
                     captured-neighbs)))))
