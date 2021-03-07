(ns maze-project.models.cell)

(defrecord MazeCell [north east south west])
(defrecord CellPos [row col])

(defn create-cell [n e s w]
  (MazeCell. n e s w))