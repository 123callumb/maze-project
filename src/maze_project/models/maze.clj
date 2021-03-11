(ns maze-project.models.maze
  (:require [maze-project.models.grid :refer [create-grid get-rnd-grid-pos]]
            [maze-project.algorithms.maze-creation.binary-tree :refer [binary-tree]]
            [maze-project.algorithms.maze-creation.aldous-broder :refer [aldous-broder]]
            [maze-project.algorithms.maze-creation.reverse-backtracking :refer [reverse-backtracking]]))

(defrecord MazeGrid [grid startPos endPos])

; Definitions for maze algorithm names, set as a variable so they are not loosely defined
; and can be referenced elsewhere
(def alg-name-binary-tree "Binary Tree")
(def alg-name-aldous-broder "Aldous Broder")
(def alg-name-reverse-backtrack  "Reverse Backtracking")
(def get-maze-names [alg-name-binary-tree alg-name-aldous-broder alg-name-reverse-backtrack])

; Global maze state
(def CurrentMaze (atom {}))

; Set method for altering global maze state
(defn set-maze [newMaze]
  (swap! CurrentMaze (fn[old new] new) newMaze))

; Create a grid and apply a given algorithm to the grid
(defn create-and-set-maze [rows cols mazeName shapeName]
  (let [grid (create-grid rows cols shapeName)
        maze (cond
               (= mazeName alg-name-binary-tree) (binary-tree grid)
               (= mazeName alg-name-aldous-broder) (aldous-broder grid)
               (= mazeName alg-name-reverse-backtrack) (reverse-backtracking grid))
        startPos (get-rnd-grid-pos grid)
        endPos (get-rnd-grid-pos grid)
        generatedMaze (MazeGrid. maze startPos endPos)]
    (set-maze generatedMaze)))

; Get global maze
(defn get-maze [] @CurrentMaze)

(defn maze-exists [] (not (nil? @CurrentMaze)))