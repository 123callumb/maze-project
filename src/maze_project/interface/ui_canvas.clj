(ns maze-project.interface.ui-canvas
  (:use [seesaw.core]
        [seesaw.color])
  (:require [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.maze-creation.reverse-backtracking :refer [reverse-backtracking]]
            [maze-project.algorithms.maze-creation.aldous-broder :refer [aldous-broder]]
            [maze-project.algorithms.maze-creation.binary-tree :refer [binary-tree]]
            [maze-project.interface.ui-grid :refer [draw-grid draw-journey]]
            [maze-project.algorithms.maze-solving.depth-first :refer [depth-first]]))

(defn get-maze-canvas [frame] (select frame [:#mazeCanvas]))

(def get-maze-names ["Binary Tree" "Aldous Broder" "Reverse Backtracking"])

; Maybe move this to a generic maze file
(defn create-maze [mazeName rows cols]
  (let [grid (create-grid rows cols)]
    (cond
      (= mazeName (get-maze-names 0)) (binary-tree grid)
      (= mazeName (get-maze-names 1)) (aldous-broder grid)
      (= mazeName (get-maze-names 2)) (reverse-backtracking grid))))

; Might have to take this out when solving comes into play as it will need more layers on the graphics draw func
(defn draw-maze [frame mazeName rows cols]
  (let [maze (create-maze mazeName rows cols)
        canvas (get-maze-canvas frame)
        journey (depth-first maze)]
    (config! canvas :paint (fn[c g] [(draw-journey g c rows cols journey) (draw-grid maze c g)]))))

(defn create-canvas []
  (canvas :id :mazeCanvas :background (color "#00d6b9") :paint nil :border 0))