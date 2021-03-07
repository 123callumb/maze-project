(ns maze-project.interface.ui-grid
  (:use [seesaw.graphics]
        [seesaw.color])
  (:require [maze-project.interface.ui-cell :refer [draw-cell]]))

(defn highlight-pos [g pos cellWidth cellHeight clr]
  (let [padH (/ cellHeight 2)
        padW (/ cellWidth 2)
        x (+ (* (:row pos) cellWidth) (/ padW 2))
        y (+ (* (:col pos) cellHeight) (/ padH 2))
        w (- cellWidth padW)
        h (- cellHeight padH)]
    (draw g (ellipse x y w h) (style :background clr))))

(defn draw-grid [mazeGrid canvas g]
  (let [h (.getHeight canvas)
        w (.getWidth canvas)
        grid (:grid mazeGrid)
        mazeStart (:startPos mazeGrid)
        mazeEnd (:endPos mazeGrid)
        rowCount (count grid)
        colCount (count (grid 0))
        cellWidth (int (Math/floor (/ w colCount)))
        cellHeight (int (Math/floor (/ h rowCount)))]
    (loop [row 0 col 0]
      (let [cell ((grid row) col)]
        (draw-cell cell g (* col cellWidth) (* row cellHeight) cellWidth cellHeight)
        (if (and (= row (dec rowCount)) (= col (dec colCount)))
          [(highlight-pos g mazeStart cellWidth cellHeight (color 72 219 131))
           (highlight-pos g mazeEnd cellWidth cellHeight (color 232 71 71))]
          (if (= col (dec colCount))
            (recur (inc row) 0)
            (recur row (inc col))))))))
