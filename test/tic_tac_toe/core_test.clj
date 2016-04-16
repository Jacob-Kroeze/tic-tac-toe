(ns tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.core :refer :all]))

(def test-board
  [[:a3 :b3 :c3]
   [:a2 :b2 :c2]
   [:a1 :b1 :c1]])

(deftest gets-index-of-a
  (testing "Get the index of :a in the alphabet should be 0"
    (is (= 0
           (alpha-index :a)))))

(deftest write-an-x
  (testing "Write an x at position returns a new board-vec with x at position
           a board is like chess algebraic notation"
    (is (= [[nil nil nil]
            [nil nil nil]
            [:x  nil nil]]
           (x :a 1))))) 

(deftest get-a1-diagonal
  (testing "Return a boards diagonal starting at a1, which is the bottom left")
  (is (= '(:c3 :b2 :a1)
         (a1-diagonal test-board))))

(deftest get-c1-diagonal
  (testing "Return a board's diagonal starting at c3, which is the top right")
  (is (= '(:a3 :b2 :c1)
         (c1-diagonal test-board))))

(deftest can-tally-col-a
  (testing "Count xs and ys in each row, col, and two diagonals,
   {:a [[row] [col] [diag]] :y [[row] [col] [diag]]}"
     (is (= {:x {:rows [1 1 1]}}
           (do (x :a 1)
               (x :a 2)
               (x :a 3))
           (tally board )))))
