(ns maze-project.algorithms.aldous-broder)

; Randomly gets the next neighbour cell to go to by returning its position.
; Makes sure the next positions returned do not go outside of the maze
(defn get-next-cell-pos [currentRow currentCol maxRow maxCol]
    (loop []
      (let [changeRow (= (rand-int 2) 0)
            incPos (= (rand-int 2) 0)
            nextCol (if (changeRow) currentCol (if (incPos) (inc currentCol) (dec currentCol)))
            nextRow (if (changeRow) (if (incPos) (inc currentRow) (dec currentRow)) currentRow)]
        (if (and (>= nextRow 0) (< nextRow maxRow) (>= nextCol 0) (< nextCol maxCol))
          {:nextRow nextRow :nextCol nextCol}
          (recur)))))

; Use the current cell position and the next cell position to find which walls
; need to be broken
(defn find-walls-to-break [currentRow currentCol neighbourRow neighbourCol]
  (let [currentWall (cond
                      (< neighbourCol currentCol) :west
                      (> neighbourCol currentCol) :east
                      (< neighbourRow currentRow) :north
                      :else :south)
        wallNeighbours {:north :south :east :west :south :north :west :east}
        neighbourWall (currentWall wallNeighbours)]
    {:current currentWall :neighbour neighbourWall}))

; See if a cell at a given position has already been visited
(defn cell-already-visited [cell]
  (or (= (:north cell) 1) (= (:east cell) 1) (= (:west cell) 1) (= (:south cell) 1)))

(defn begin-journey [gridMaze startRow startCol maxRows maxCols]
  (let [cellsToVisit (* maxRows maxCols)])
  (loop [grid gridMaze row startRow col startCol cellsModified 1]
    (let [nextCellPos (get-next-cell-pos row col maxRows maxCols)])))

(defn aldous-broder [gridMaze]
  (let [rows (count gridMaze)
        cols (count (gridMaze 0))
        startRow (rand-int rows)
        startCol (rand-int cols)]))