(ns maze-project.algorithms.maze-creation.reverse-backtracking
  (:require [maze-project.algorithms.maze-creation.alg-helper :refer [cell-already-visited break-walls]]
            [maze-project.models.grid :refer [get-valid-cell-count get-rnd-grid-pos]]
            [maze-project.models.cell])
  (:import (maze_project.models.cell CellPos)))

; Looks at all four potential neighbours to a cell and will return all of the
; cells that have not yet been visited.
(defn get-non-visited-neighbours [grid row col maxRow maxCol]
  (let [lCol (dec col)
        lRow (dec row)
        hCol (inc col)
        hRow (inc row)
        northVisited (if (>= lRow 0) (cell-already-visited ((grid lRow) col)) true)
        eastVisited (if (< hCol maxCol) (cell-already-visited ((grid row) hCol)) true)
        southVisited (if (< hRow maxRow) (cell-already-visited ((grid hRow) col)) true)
        westVisited (if (>= lCol 0) (cell-already-visited ((grid row) lCol)) true)]
    (vec (filter some? [(if (not northVisited) (CellPos. lRow col))
                        (if (not eastVisited) (CellPos. row hCol))
                        (if (not southVisited) (CellPos. hRow col))
                        (if (not westVisited) (CellPos. row lCol))]))))

; Reverse back tracking will do something similar to aldous broder but will turn
; back on itself. There is a journey variable in the algorithm that is used to turn back.
; It also makes sure not to revisit already visited cells, unless they are part
; of the reverse journey.
(defn reverse-backtracking [gridMaze]
  (let [maxRows (count gridMaze)
        maxCols (count (gridMaze 0))
        startPos (get-rnd-grid-pos gridMaze)
        startRow (:row startPos)
        startCol (:col startPos)
        cellsToVisit (get-valid-cell-count gridMaze)]
    (loop [grid gridMaze row startRow col startCol cellsVisited 1 journey [(CellPos. row col)]]
      (if (and (= cellsVisited cellsToVisit))
        grid
        (let [nonVisitedNeighbours (get-non-visited-neighbours grid row col maxRows maxCols)
              nonVisitedAmount (count nonVisitedNeighbours)]
          (if (> nonVisitedAmount 0)
            (let [nextPosToVisit (nonVisitedNeighbours  (rand-int nonVisitedAmount))
                  nextRow (:row nextPosToVisit)
                  nextCol (:col nextPosToVisit)
                  updatedJourney (conj journey (CellPos. nextRow nextCol))
                  updatedGrid (break-walls grid row col nextRow nextCol)]
              (recur updatedGrid nextRow nextCol (inc cellsVisited) updatedJourney))
            (let [prevCell (last journey)
                  updatedJourney (filter #(not= % prevCell) journey)
                  prevRow (:row prevCell)
                  prevCol (:col prevCell)]
              (recur grid prevRow prevCol cellsVisited updatedJourney))))))))