(ns maze-project.algorithms.maze-creation.aldous-broder
  (:require [maze-project.algorithms.maze-creation.alg-helper :refer [cell-already-visited break-walls]]
            [maze-project.models.grid :refer [get-rnd-grid-pos get-valid-cell-count]]))

; Randomly gets the next neighbour cell to go to by returning its position.
; Makes sure the next positions returned do not go outside of the maze
(defn get-next-rnd-cell-pos [currentRow currentCol maxRow maxCol grid]
  (loop []
    (let [changeRow (= (rand-int 2) 0)
          incPos (= (rand-int 2) 0)
          nextCol (if changeRow currentCol (if incPos (inc currentCol) (dec currentCol)))
          nextRow (if changeRow (if incPos (inc currentRow) (dec currentRow)) currentRow)]
      (if (and (>= nextRow 0) (< nextRow maxRow)
               (>= nextCol 0) (< nextCol maxCol)
               (not (:ignore ((grid nextRow) nextCol))))
        {:nextRow nextRow :nextCol nextCol}
        (recur)))))


(defn aldous-broder [gridMaze]
  (let [maxRows (count gridMaze)
        maxCols (count (gridMaze 0))
        startPos (get-rnd-grid-pos gridMaze)
        startRow (:row startPos)
        startCol (:col startPos)
        cellsToVisit (get-valid-cell-count gridMaze)]
    (loop [grid gridMaze row startRow col startCol cellsModified 1]
      (if (= cellsModified cellsToVisit)
        grid
        (let [nextCellPos (get-next-rnd-cell-pos row col maxRows maxCols grid)
              nextRow (:nextRow nextCellPos)
              nextCol (:nextCol nextCellPos)
              cell ((grid nextRow) nextCol)
              alreadyVisited (cell-already-visited cell)]
          (if (not alreadyVisited)
            (recur (break-walls grid row col nextRow nextCol) nextRow nextCol (inc cellsModified))
            (recur grid nextRow nextCol cellsModified)))))))