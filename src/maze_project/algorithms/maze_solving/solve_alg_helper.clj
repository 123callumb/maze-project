(ns maze-project.algorithms.maze-solving.solve-alg-helper
 (:import (maze_project.models.cell CellPos)))

(defn get-cells-non-visited-pathways [cell row col visitedCells]
  (let [cellsToVisit [(if (= (:north cell) 1) (CellPos. (dec row) col))
                      (if (= (:east cell) 1) (CellPos. row (inc col)))
                      (if (= (:south cell) 1) (CellPos. (inc row) col))
                      (if (= (:west cell) 1) (CellPos. row (dec col)))]
        removeVisited (filter #(and (not= % nil) (not-any? (fn [visited] (= visited %)) visitedCells)) cellsToVisit)]
    (vec removeVisited)))

(defn depth-first [gridMaze]
  (let [mazeStartPos (:startPos gridMaze)
        mazeEndPos (:endPos gridMaze)
        startRow (:row mazeStartPos)
        startCol (:col mazeStartPos)
        endRow (:row mazeEndPos)
        endCol (:col mazeEndPos)
        grid (:grid gridMaze)]
    (loop [row startRow col startCol journey [mazeStartPos] visitedCells []]
      (if (and (= row endRow) (= col endCol))
        journey
        (let [cell ((grid row) col)
              possiblePathways (get-cells-non-visited-pathways cell row col visitedCells)
              pathwayCount (count possiblePathways)]
          (if (> pathwayCount 0)
            (let [rndPathway (possiblePathways (rand-int pathwayCount))
                  updatedJourney (conj journey rndPathway)
                  nextRow (:row rndPathway)
                  nextCol (:col rndPathway)
                  updatedVisitedCells (conj visitedCells rndPathway)]
              (recur nextRow nextCol updatedJourney updatedVisitedCells))
            (let [prevPos (last journey)
                  updatedJourney (filter #(not= prevPos %) journey)
                  prevRow (:row prevPos)
                  prevCol (:col prevPos)]
              (recur prevRow prevCol updatedJourney visitedCells))))))))


