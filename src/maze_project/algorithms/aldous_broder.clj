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
; Might move this to a global helper, seems to be quite relevant for all wall
; breaking, along with the break-walls function too
(defn find-walls-to-break [row col nRow nCol]
  (let [currentWall (cond
                      (< nCol col) :west
                      (> nCol col) :east
                      (< nRow row) :north
                      :else :south)
        wallNeighbours {:north :south :east :west :south :north :west :east}
        neighbourWall (currentWall wallNeighbours)]
    {:current currentWall :neighbour neighbourWall}))

; See if a cell at a given position has already been visited
(defn cell-already-visited [cell]
  (or (= (:north cell) 1) (= (:east cell) 1) (= (:west cell) 1) (= (:south cell) 1)))

(defn break-walls [grid row col nRow nCol]
  (let [wallsToBreak (find-walls-to-break row col nRow nCol)
        currentWall (:current wallsToBreak)
        neighbourWall (:neighbour wallsToBreak)]
    (assoc-in (assoc-in grid [row col currentWall] 1) [nRow nCol neighbourWall] 1)))

(defn aldous-broder [gridMaze]
  (let [maxRows (count gridMaze)
        maxCols (count (gridMaze 0))
        startRow (rand-int maxRows)
        startCol (rand-int maxCols)
        cellsToVisit (* maxRows maxCols)]
    (loop [grid gridMaze row startRow col startCol cellsModified 1]
      (let [nextCellPos (get-next-cell-pos row col maxRows maxCols)
            nextRow (:nextRow nextCellPos)
            nextCol (:nextCol nextCellPos)
            cell ((grid nextRow) nextCol)
            alreadyVisited (cell-already-visited cell)]
        (println "Modified " cellsModified " cells out of " cellsToVisit)
        (if (not alreadyVisited)
          (recur (break-walls grid row col nextRow nextCol) nextRow nextCol (inc cellsModified))
          (recur grid nextRow nextCol cellsModified))))))