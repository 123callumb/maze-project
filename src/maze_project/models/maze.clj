(ns maze-project.models.maze
  (:require [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.maze-creation.binary-tree :refer [binary-tree]]
            [maze-project.algorithms.maze-creation.aldous-broder :refer [aldous-broder]]
            [maze-project.algorithms.maze-creation.reverse-backtracking :refer [reverse-backtracking]]))

(def get-maze-names ["Binary Tree" "Aldous Broder" "Reverse Backtracking"])

(defn create-maze [mazeName rows cols]
  (let [grid (create-grid rows cols)]
    (cond
      (= mazeName (get-maze-names 0)) (binary-tree grid)
      (= mazeName (get-maze-names 1)) (aldous-broder grid)
      (= mazeName (get-maze-names 2)) (reverse-backtracking grid))))

(def CurrentMaze (atom {}))

(defn set-maze [rows cols algName]
  (let [newMaze (create-maze algName rows cols)]
    (swap! CurrentMaze (fn[old new] new) newMaze)))


(defn get-maze [] @CurrentMaze)

(defn maze-exists [] (not (nil? @CurrentMaze)))