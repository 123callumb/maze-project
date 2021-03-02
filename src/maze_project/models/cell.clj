(ns maze-project.models.cell)

(defrecord MazeCell [north east south west])

(defn create-cell [n e s w]
  (MazeCell. n e s w))