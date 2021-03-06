(ns maze-project.algorithms.aldous-broder)

(defn get-next-cell-pos [currentRow currentCol maxRow maxCol]
    (loop
      (let [changeRow (= (rand-int 2) 0)
            incPos (= (rand-int 2) 0)
            nextCol (if (changeRow) currentCol (if (incPos) (inc currentCol) (dec currentCol)))
            nextRow (if (changeRow) (if (incPos) (inc currentRow) (dec currentRow)) currentRow)]
        (if (and (>= nextRow 0) (< nextRow maxRow) (>= nextCol 0) (< nextCol maxCol))
          [:nextRow nextRow :nextCol nextCol]
          (recur)))))

(defn begin-journey [gridMaze startRow startCol]
  (loop [grid gridMaze row startRow col startCol]))

(defn aldous-broder [gridMaze]
  (let [rows (count gridMaze)
        cols (count (gridMaze 0))
        startRow (rand-int rows)
        startCol (rand-int cols)]))