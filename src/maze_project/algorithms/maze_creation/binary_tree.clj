(ns maze-project.algorithms.maze-creation.binary-tree
  (:require [maze-project.models.grid :refer [get-rnd-grid-pos]]))

(defn open-cell-north [grid row col]
  (assoc-in (assoc-in grid [row col :north] 1) [(dec row) col :south] 1))

(defn open-cell-west [grid row col]
  (assoc-in (assoc-in grid [row col :west] 1) [row (dec col) :east] 1))

; Top down open cell approach
(defn open-cell [grid row col]
  (cond
    (and (= col 0) (= row 0)) grid
    (= row 0) (open-cell-west grid row col)
    (= col 0) (open-cell-north grid row col)
    (= 0 (rand-int 2)) (open-cell-north grid row col)
    :else (open-cell-west grid row col)))

(defn walk-row [gridMaze row]
  (let [colAmount (count (gridMaze row))]
    (loop [grid gridMaze colIndex 0]
      (if (= colIndex colAmount)
        grid
        (if (:ignore ((grid row) colIndex))
          (recur grid (inc colIndex))
          (recur (open-cell grid row colIndex) (inc colIndex)))))))


(defn binary-tree [gridMaze]
  (let [rowAmount (count gridMaze)]
    (loop [grid gridMaze rowIndex 0]
      (if (= rowIndex rowAmount)
        grid
        (recur (walk-row grid rowIndex) (inc rowIndex))))))