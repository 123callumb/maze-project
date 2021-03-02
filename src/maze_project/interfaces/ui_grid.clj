(ns maze-project.interfaces.ui-grid
  (:require [maze-project.interfaces.ui-cell :refer [draw-cell]]))

(defn draw-grid [grid canvas g]
  (let [w (.getWidth canvas)
        rowCount (count grid)
        colCount (count (grid 0))
        smallestDimension (if (< rowCount colCount) rowCount colCount)
        cellSize (int (Math/floor (/ w smallestDimension)))]
   (loop [row 0 col 0]
     (let [cell ((grid row) col)]
       (println "Cell drawn" col row (* col cellSize) (* row cellSize) cellSize)
       (draw-cell cell g (* col cellSize) (* row cellSize) cellSize)
       (cond
         (and (= row (dec rowCount)) (= col (dec colCount))) nil
         (= col (dec colCount)) (recur (inc row) 0)
         :else
         (recur row (inc col)))))))
