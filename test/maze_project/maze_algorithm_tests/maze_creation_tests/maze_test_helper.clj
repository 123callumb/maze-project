(ns maze-project.maze-algorithm-tests.maze-creation-tests.maze-test-helper
  (:require [clojure.test :refer :all]
            [maze-project.algorithms.maze-solving.depth-first :refer [get-cells-non-visited-pathways]]
            [maze-project.models.grid :refer [get-valid-cell-count]]
            [maze-project.models.cell])
  (:import (maze_project.models.cell CellPos)))

; Using part of the functionality from how the maze is solved, the same
; type of algorithm (depth first) can be used to make sure that the maze is a fully
; connected maze.
;
; My solving algorithm stops once the end position has been
; located. However, this algorithm is not looking for an exit, it simply stops once all cells
; have been visited.

; This method takes a very ling time on larger mazes as it needs to make sure
; every cell is visitable from every position
(defn maze-is-fully-linked [grid]
  (let [startRow (int (Math/floor (/ (count grid) 2)))
        startCol (int (Math/floor (/ (count (grid 0)) 2)))
        validCellCount (get-valid-cell-count grid)]
    (loop [row startRow col startCol visited [] journey [(CellPos. startRow startCol)] journeyPos 0]
      (if (= (count visited) validCellCount)
        true
        (let [currentCell ((grid row) col)
              neighbours (get-cells-non-visited-pathways currentCell row col visited)
              neighbourCount (count neighbours)
              updatedVisited (if (some #(= % (CellPos. row col)) visited) visited (conj visited (CellPos. row col)))]
          (println (count visited) "/" validCellCount)
          (if (= neighbourCount 0)
            (let [prevJourneyPos (dec journeyPos)
                  prevPos (journey prevJourneyPos)
                  prevRow (:row prevPos)
                  prevCol (:col prevPos)]
              (recur prevRow prevCol updatedVisited journey prevJourneyPos))
            (let [nextPos (neighbours (rand-int neighbourCount))
                  nextRow (:row nextPos)
                  nextCol (:col nextPos)
                  updatedJourney (conj journey nextPos)]
              (recur nextRow nextCol updatedVisited updatedJourney (count journey)))))))))