(ns maze-project.models.grid
  (:require [maze-project.models.cell :refer [create-cell]])
  (:import (maze_project.models.cell CellPos)))

(def get-grid-shape-names ["Rectangle" "Ellipse"])
(defrecord MazeGrid [grid startPos endPos])

(defn create-rect-grid [rows cols]
  (MazeGrid.
    (vec (repeat rows (vec (repeat cols (create-cell 0 0 0 0 false)))))
    (CellPos. 0 0)
    (CellPos. 0 0)))

(defn pos-is-in-ellipse [rows cols row col]
  (let [ellipseH (int (Math/floor (/ rows 2)))
        ellipseW (int (Math/floor (/ cols 2)))
        aSqr (Math/pow ellipseW 2)
        bSqr (Math/pow ellipseH 2)
        left (/ (Math/pow (- col ellipseW) 2) aSqr)
        right (/ (Math/pow (- row ellipseH) 2) bSqr)]
    (<= (+ left right) 1)))

(defn create-ellipse-grid [rows cols]
  (loop [row 0 col 0 grid [] currentRow []]
    (if (and (= row rows) (= col cols))
      (MazeGrid. grid (CellPos. 0 0) (CellPos. 0 0))
      (if (= col cols)
        (recur (inc row) 0 (conj grid currentRow) [])
        (let [ignoreCell (not (pos-is-in-ellipse rows cols row col))
              currentCell (create-cell 0 0 0 0 ignoreCell)]
          (recur row (inc col) grid (conj currentRow currentCell)))))))

(defn create-grid [rows cols shapeName]
  (cond
    (= (get-grid-shape-names 0) shapeName) (create-rect-grid rows cols)
    (= (get-grid-shape-names 1) shapeName) (create-ellipse-grid rows cols)))