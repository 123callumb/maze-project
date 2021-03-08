(ns maze-project.interface.ui-grid
  (:use [seesaw.graphics]
        [seesaw.color])
  (:require [maze-project.interface.ui-cell :refer [draw-cell]]))

(defn highlight-pos [g pos cellWidth cellHeight clr]
  (let [padH (int (Math/floor (/ cellHeight 1.5)))
        padW (int (Math/floor (/ cellWidth 1.5)))
        x (+ (* (:col pos) cellWidth) (/ padW 2))
        y (+ (* (:row pos) cellHeight) (/ padH 2))
        w (- cellWidth padW)
        h (- cellHeight padH)]
    (draw g (rect x y w h) (style :background clr))))

(defn draw-journey [g canvas maxRows maxCols journey]
  (let [h (.getHeight canvas)
        w (.getWidth canvas)
        cellHeight (int (Math/floor (/ h maxRows)))
        cellWidth (int (Math/floor (/ w maxCols)))
        journeySize (count journey)]
    (loop [index 0]
      (if (= index journeySize)
        nil
        (let [cellPos (journey index)]
          (highlight-pos g cellPos cellWidth cellHeight (color 0 255 221))
          (recur (inc index)))))))

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
