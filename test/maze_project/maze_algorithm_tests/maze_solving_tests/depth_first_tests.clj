(ns maze-project.maze-algorithm-tests.maze-solving-tests.depth-first-tests
  (:require [clojure.test :refer :all]
            [maze-project.algorithms.maze-solving.depth-first :refer :all]
            [maze-project.models.maze :refer [create-and-set-maze
                                              alg-name-aldous-broder
                                              alg-name-reverse-backtrack
                                              alg-name-binary-tree
                                              get-maze]]
            [maze-project.models.cell :refer [create-cell]])
  (:import [maze_project.models.cell CellPos]))

(deftest get-cells-non-visited-pathways-tests
    (testing "Surrounding cells are all unvisted"
      (let [row 4
            col 5
            cell (create-cell 1 1 1 1 false)
            result (get-cells-non-visited-pathways cell row col [])]
        (is (= (count result) 4))))
    (testing "All surrounding cells have been visited"
      (let [row 4
            col 5
            cell (create-cell 1 1 1 1 false)
            visitedCells [(CellPos. (dec row) col)
                          (CellPos. (inc row) col)
                          (CellPos. row (dec col))
                          (CellPos. row (inc col))]
            result (get-cells-non-visited-pathways cell row col visitedCells)]
        (is (= (count result) 0)))))

; Get the end position of the maze and check to see if the journey
; has gotten to the final cells neighbours
(defn is-maze-solved [maze]
  (let [journeyResult (depth-first maze)
        endPos (:endPos maze)
        endRow (:row endPos)
        endCol (:col endPos)
        endCell (((:grid maze) endRow) endCol)
        possibleNeighbours (get-cells-non-visited-pathways endCell endRow endCol [])]
    (some (fn [journeyCell] (some #(= % journeyCell) possibleNeighbours)) journeyResult)))

; Also randomising the size of the maze each time for additional testing
(deftest depth-first-tests
  (testing "Testing on binary tree rectangle"
    (create-and-set-maze 10 10 alg-name-binary-tree "Rectangle")
    (is (is-maze-solved (get-maze))))
  (testing "Testing on aldous broder rectangle"
    (create-and-set-maze 8 12 alg-name-aldous-broder "Rectangle")
    (is (is-maze-solved (get-maze))))
  (testing "Testing on reverse backtracking rectangle"
    (create-and-set-maze 14 8 alg-name-reverse-backtrack "Rectangle")
    (is (is-maze-solved (get-maze))))
  (testing "Testing on aldous broder ellipse"
    (create-and-set-maze 6 6 alg-name-aldous-broder "Ellipse")
    (is (is-maze-solved (get-maze))))
  (testing "Testing on reverse backtracking ellipse"
    (create-and-set-maze 20 20 alg-name-reverse-backtrack "Ellipse")
    (is (is-maze-solved (get-maze)))))


