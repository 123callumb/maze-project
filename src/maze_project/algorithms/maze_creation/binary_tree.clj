(ns maze-project.algorithms.maze-creation.binary-tree
  (:require [maze-project.models.grid :refer [create-grid]])
  (:import (maze_project.models.grid MazeGrid)
           (maze_project.models.cell CellPos)))

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
        (recur (open-cell grid row colIndex) (inc colIndex))))))

(defn binary-tree [gridMaze]
  (let [gridMaze (:grid gridMaze)
        rowAmount (count gridMaze)]
    (loop [grid gridMaze rowIndex 0]
      (if (= rowIndex rowAmount)
        (MazeGrid. grid (CellPos. 0 0) (CellPos. (dec rowAmount) (dec (count (grid 0)))))
        (recur (walk-row grid rowIndex) (inc rowIndex))))))