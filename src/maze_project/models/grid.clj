(ns maze-project.models.grid
  (:require [maze-project.models.cell :refer [create-cell]]))

(defn create-grid-row [cellAmount]
  (vec (repeat cellAmount (create-cell 0 0 0 0))))

(defn create-grid [rows cols]
  (vec (repeat rows (create-grid-row cols))))