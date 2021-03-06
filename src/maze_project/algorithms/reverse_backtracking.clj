(ns maze-project.algorithms.reverse-backtracking
  (:require [maze-project.algorithms.alg-helper :refer [get-next-rnd-cell-pos cell-already-visited break-walls]])
  (:import (maze_project.algorithms.alg_helper CellPosResponse)))

(defn get-non-visited-neighbours [grid row col maxRow maxCol]
  (let [lCol (dec col)
        lRow (dec row)
        hCol (inc col)
        hRow (inc row)
        northVisited (if (>= lRow 0) (cell-already-visited ((grid lRow) col)) true)
        eastVisited (if (< (inc col) maxCol) (cell-already-visited ((grid row) hCol)) true)
        southVisited (if (< (inc row) maxRow) (cell-already-visited ((grid hRow) col)) true)
        westVisited (if (>= (dec col) 0)) (cell-already-visited ((grid row) lCol)) true]
    (vec (filter some? [(if (not northVisited) (CellPosResponse. lRow col))
                        (if (not eastVisited) (CellPosResponse. row hCol))
                        (if (not southVisited) (CellPosResponse. hRow col))
                        (if (not westVisited) (CellPosResponse. row lCol))]))))

(defn reverse-backtracking [gridMaze]
  (let [maxRows (count grid)
        maxCols (count (grid 0))
        startRow (rand-int maxRows)
        startCol (rand-int maxCols)
        cellsToVisit (* maxRows maxCols)]
    (loop [grid gridMaze row startRow col startCol cellsVisited 1]
      (if (and (= cellsVisited cellsToVisit) (= startCol prevCol) (= startRow prevRow))
        grid
        (let [nonVisitedNeighbours (get-non-visited-neighbours grid row col maxRows maxCols)
              nonVisitedAmount (count nonVisitedNeighbours)]
          (if (> nonVisitedAmount 0)
            (let [nextPosToVisit (nonVisitedNeighbours  (rand-int nonVisitedAmount))
                  nextRow (:row nextPosToVisit)
                  nextCol (:col nextPosToVisit)]
              (recur (break-walls grid row col nextRow nextCol) nextRow nextCol (inc cellsVisited)))
            (recur grid prevRow prevCol)))))))