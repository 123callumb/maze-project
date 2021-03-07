(ns maze-project.models.grid
  (:require [maze-project.models.cell :refer [create-cell]])
  (:import (maze_project.models.cell CellPos)))

(defrecord MazeGrid [grid startPos endPos])

(defn create-grid-row [cellAmount]
  (vec (repeat cellAmount (create-cell 0 0 0 0))))

(defn create-grid [rows cols]
  (MazeGrid. (vec (repeat rows (create-grid-row cols))) (CellPos. 0 0) (CellPos. 0 0)))