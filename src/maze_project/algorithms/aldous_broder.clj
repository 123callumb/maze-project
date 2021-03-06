(ns maze-project.algorithms.aldous-broder
  (:require [maze-project.algorithms.alg-helper :refer [cell-already-visited break-walls]]))

; Randomly gets the next neighbour cell to go to by returning its position.
; Makes sure the next positions returned do not go outside of the maze
(defn get-next-rnd-cell-pos [currentRow currentCol maxRow maxCol]
  (loop []
    (let [changeRow (= (rand-int 2) 0)
          incPos (= (rand-int 2) 0)
          nextCol (if changeRow currentCol (if incPos (inc currentCol) (dec currentCol)))
          nextRow (if changeRow (if incPos (inc currentRow) (dec currentRow)) currentRow)]
      (if (and (>= nextRow 0) (< nextRow maxRow) (>= nextCol 0) (< nextCol maxCol))
        {:nextRow nextRow :nextCol nextCol}
        (recur)))))


(defn aldous-broder [gridMaze]
  (let [maxRows (count gridMaze)
        maxCols (count (gridMaze 0))
        startRow (rand-int maxRows)
        startCol (rand-int maxCols)
        cellsToVisit (* maxRows maxCols)]
    (loop [grid gridMaze row startRow col startCol cellsModified 1]
      (if (= cellsModified cellsToVisit)
        grid
        (let [nextCellPos (get-next-rnd-cell-pos row col maxRows maxCols)
              nextRow (:nextRow nextCellPos)
              nextCol (:nextCol nextCellPos)
              cell ((grid nextRow) nextCol)
              alreadyVisited (cell-already-visited cell)]
          (if (not alreadyVisited)
            (recur (break-walls grid row col nextRow nextCol) nextRow nextCol (inc cellsModified))
            (recur grid nextRow nextCol cellsModified)))))))