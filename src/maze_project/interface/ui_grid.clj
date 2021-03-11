(ns maze-project.interface.ui-grid
  (:use [seesaw.graphics]
        [seesaw.color])
  (:require [maze-project.interface.ui-cell :refer [draw-cell]]))

; File is for drawing arrays of cell positions, each method seems pretty self explanatory
; Most methods use a loop to iterate across a grid. Methods return nil as they are void methods.

(defn highlight-pos [g pos cellWidth cellHeight clr]
  (let [x (* (:col pos) cellWidth)
        y (* (:row pos) cellHeight)]
    (draw g (rect  x y cellWidth cellHeight) (style :background clr))))

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
          (highlight-pos g cellPos cellWidth cellHeight (color 249 192 34 200))
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
    (highlight-pos g mazeStart cellWidth cellHeight (color 30 204 18))
    (highlight-pos g mazeEnd cellWidth cellHeight (color 232 71 71))
    (loop [row 0 col 0]
      (let [cell ((grid row) col)]
        (draw-cell cell g (* col cellWidth) (* row cellHeight) cellWidth cellHeight)
        (if (and (= row (dec rowCount)) (= col (dec colCount)))
          nil
          (if (= col (dec colCount))
            (recur (inc row) 0)
            (recur row (inc col))))))))