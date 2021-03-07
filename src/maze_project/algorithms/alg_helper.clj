(ns maze-project.algorithms.alg-helper)

; This is a helper class, these functions will be useful in most maze creation algorithms

; Takes the current cell position and the next cell position and returns which
; walls need to be broken to link them together.
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

; Break a wall between a two cells and return the updated grid
(defn break-walls [grid row col nRow nCol]
  (let [wallsToBreak (find-walls-to-break row col nRow nCol)
        currentWall (:current wallsToBreak)
        neighbourWall (:neighbour wallsToBreak)]
    (assoc-in (assoc-in grid [row col currentWall] 1) [nRow nCol neighbourWall] 1)))