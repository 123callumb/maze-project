(ns maze-project.models.cell
  (:require [clojure.string :as str]))

(defrecord MazeCell [north east south west])
(defrecord CellPos [row col])

(defn create-cell [n e s w]
  (MazeCell. n e s w))

(defn print-cell-to-str [cell]
  (str (:north cell) "," (:east cell) "," (:south cell) "," (:west cell) "|"))

(defn get-cell-from-str [cellStr]
  (let [points (str/split cellStr #",")]
    (apply ->MazeCell points)))