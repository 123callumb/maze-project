(ns maze-project.models.maze
  (:require [maze-project.models.grid :refer [create-grid get-rnd-grid-pos]]
            [maze-project.algorithms.maze-creation.binary-tree :refer [binary-tree]]
            [maze-project.algorithms.maze-creation.aldous-broder :refer [aldous-broder]]
            [maze-project.algorithms.maze-creation.reverse-backtracking :refer [reverse-backtracking]]))

(defrecord MazeGrid [grid startPos endPos])

(def get-maze-names ["Binary Tree" "Aldous Broder" "Reverse Backtracking"])

(def CurrentMaze (atom {}))

(defn set-maze [newMaze]
  (swap! CurrentMaze (fn[old new] new) newMaze))

(defn create-and-set-maze [rows cols mazeName shapeName]
  (let [grid (create-grid rows cols shapeName)
        maze (cond
               (= mazeName (get-maze-names 0)) (binary-tree grid)
               (= mazeName (get-maze-names 1)) (aldous-broder grid)
               (= mazeName (get-maze-names 2)) (reverse-backtracking grid))
        startPos (get-rnd-grid-pos grid)
        endPos (get-rnd-grid-pos grid)
        generatedMaze (MazeGrid. maze startPos endPos)]
    (set-maze generatedMaze)))

(defn get-maze [] @CurrentMaze)

(defn maze-exists [] (not (nil? @CurrentMaze)))