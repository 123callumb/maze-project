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
    (create-cell
      (Integer/parseInt (points 0))
      (Integer/parseInt (points 1))
      (Integer/parseInt (points 2))
      (Integer/parseInt (points 3)))))