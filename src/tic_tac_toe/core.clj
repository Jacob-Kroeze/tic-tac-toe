(ns tic-tac-toe.core)

(def h 3)
(def w 3)

(def board
  (atom
   (into [] (repeat h
                    (into [] (repeat w nil))))))

(defn- char-range [start end]
  "letter from start to end using char notation (e.g. :backslash a)"
  (map char (range (int start) (inc (int end)))))

(defn alpha-vec [start end]
  "the alphabet in symbols"
  (vec
   (map
    #(keyword (str %))
    (char-range start end ))))

(def alphabet (alpha-vec \a \z))

(defn alpha-index [letter]
  "return the index of a letter keyword in the alphabet"
  (.indexOf alphabet letter))

(defn column-index [letter]
  (alpha-index letter))

(defn row-index [number]
  (- h number))

(defn write [piece col row]
  (swap! board assoc-in [(row-index row) (column-index col)] piece))

(defn x [col row]
  (write :x col row))

(defn o [col row]
  (write :o col row))

(def select-values (comp vals select-keys))

(defn count-xs [l]
  (count (filter #(= :x %) l)))

(defn count-os [l]
  (count (filter #(= :o %) l)))


(defn columns [board]
  (apply mapv vector board))

(defn rows [board]
  board)

(defn a1-diagonal [board]
  (map  #(get-in board %)
        (map vector
             (range h)
             (reverse (range h)))))

(defn c1-diagonal [board]
  (map  #(get-in board %)
        (map vector
             (range h)
             (range h))))

;; more readable count xs
(defn count-on-board [count-fn piece board]
  (let [cols (mapv #(count-fn %) (columns board))
        rows (mapv #(count-fn %) (rows board))
        a1-diag (mapv #(count-fn %) (a1-diagonal board))
        c1-diag (mapv #(count-fn %) (c1-diagonal board))]
    {piece
    {:cols cols
    :rows rows
    :a1-diag a1-diag
    :c1-diag c1-diag}}))

(defn count-xs-on-board [board]
  (count-on-board count-xs :x board)  )
(defn count-os-on-board [board]
  (count-on-board count-os :o board))
 
(count-xs-on-baord board)


(defn tally [board]
  "count rows, cols and diagonals for xs and "
  (merge
   (count-xs-on-board board)
   (count-os-on-board board)) )

(tally @board)

(defn winner [row-fn board]
  "if a row has three of xs or os, return winner"
  (cond
    (= 3 (count-xs (row-fn board)))  :x
    (= 3 (count-os (row-fn board))) :o
    :else false))
