(ns maze-project.algorithms.maze-solving.depth-first
  (:require [maze-project.models.cell])
  (:import (maze_project.models.cell CellPos)))

(defn get-cells-non-visited-pathways [cell row col visitedCells]
  (let [cellsToVisit [(if (= (:north cell) 1) (CellPos. (dec row) col))
                      (if (= (:east cell) 1) (CellPos. row (inc col)))
                      (if (= (:south cell) 1) (CellPos. (inc row) col))
                      (if (= (:west cell) 1) (CellPos. row (dec col)))]
        removeVisited (filter #(and (not= % nil) (not-any? (fn [visited] (= visited %)) visitedCells)) cellsToVisit)]
    (vec removeVisited)))

; This algorithm sort of uses a bit of reverse back tracking, it will reverse back track until it finds the exit position
(defn depth-first [gridMaze]
  (let [mazeStartPos (:startPos gridMaze)
        mazeEndPos (:endPos gridMaze)
        startRow (:row mazeStartPos)
        startCol (:col mazeStartPos)
        endRow (:row mazeEndPos)
        endCol (:col mazeEndPos)
        grid (:grid gridMaze)]
    (loop [row startRow col startCol journey [] visitedCells []]
      (if (and (= row endRow) (= col endCol))
        (vec (filter #(not= % (CellPos. startRow startCol)) journey))
        (let [cell ((grid row) col)
              possiblePathways (get-cells-non-visited-pathways cell row col visitedCells)
              pathwayCount (count possiblePathways)]
          (if (> pathwayCount 0)
            (let [currentCell (CellPos. row col)
                  updatedJourney (conj journey currentCell)
                  updatedVisitedCells (conj visitedCells currentCell)
                  rndPathway (possiblePathways (rand-int pathwayCount))
                  nextRow (:row rndPathway)
                  nextCol (:col rndPathway)]
              (recur nextRow nextCol updatedJourney updatedVisitedCells))
            (let [prevPos (last journey)
                  updatedJourney (pop journey)
                  updatedVisited (conj visitedCells (CellPos. row col))
                  prevRow (:row prevPos)
                  prevCol (:col prevPos)]
              (recur prevRow prevCol updatedJourney updatedVisited))))))))