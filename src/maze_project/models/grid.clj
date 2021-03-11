(ns maze-project.models.grid
  (:require [maze-project.models.cell :refer [create-cell]])
  (:import (maze_project.models.cell CellPos)))

(def get-grid-shape-names ["Rectangle" "Ellipse"])

(defn create-rect-grid [rows cols]
    (vec (repeat rows (vec (repeat cols (create-cell 0 0 0 0 false))))))

(defn pos-is-in-ellipse [rows cols row col]
  (let [ellipseH (int (Math/ceil (/ rows 2)))
        ellipseW (int (Math/ceil (/ cols 2)))
        aSqr (Math/pow ellipseW 2)
        bSqr (Math/pow ellipseH 2)
        left (/ (Math/pow (- col ellipseW) 2) aSqr)
        right (/ (Math/pow (- row ellipseH) 2) bSqr)]
    (< (+ left right) 1)))

(defn create-ellipse-grid [rows cols]
  (loop [row 0 col 0 grid [] currentRow []]
    (if (and (= row rows) (= col cols))
      grid
      (if (= col cols)
        (recur (inc row) 0 (conj grid currentRow) [])
        (let [ignoreCell (not (pos-is-in-ellipse rows cols row col))
              currentCell (create-cell 0 0 0 0 ignoreCell)]
          (recur row (inc col) grid (conj currentRow currentCell)))))))

(defn create-grid [rows cols shapeName]
  (cond
    (= (get-grid-shape-names 0) shapeName) (create-rect-grid rows cols)
    (= (get-grid-shape-names 1) shapeName) (create-ellipse-grid rows cols)))

(defn get-rnd-grid-pos [grid]
  (let [rows (count grid)
        cols (count (grid 0))
        startRow (rand-int rows)
        startCol (rand-int cols)]
    (loop [rndRow startRow rndCol startCol]
      (let [cell ((grid rndRow) rndCol) ignore (:ignore cell)]
        (if (not ignore)
          (CellPos. rndRow rndCol)
          (recur (rand-int rows) (rand-int cols)))))))

(defn get-valid-cell-count [grid]
  (reduce (fn [counter row] (reduce #(if (:ignore %2) %1 (inc %1)) counter row)) 0 grid))