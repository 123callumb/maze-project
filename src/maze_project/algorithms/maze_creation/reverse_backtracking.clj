(ns maze-project.algorithms.maze-creation.reverse-backtracking
  (:require [maze-project.algorithms.maze-creation.alg-helper :refer [cell-already-visited break-walls]])
  (:import (maze_project.models.cell CellPos)
           (maze_project.models.grid MazeGrid)))

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

(defn reverse-backtracking [gridMaze]
  (let [gridMaze (:grid gridMaze)
        maxRows (count gridMaze)
        maxCols (count (gridMaze 0))
        startRow (rand-int maxRows)
        startCol (rand-int maxCols)
        cellsToVisit (* maxRows maxCols)]
    (loop [grid gridMaze row startRow col startCol cellsVisited 1 journey [(CellPos. row col)]]
      (if (and (= cellsVisited cellsToVisit))
        (MazeGrid. grid (CellPos. startRow startCol) (CellPos. row col))
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