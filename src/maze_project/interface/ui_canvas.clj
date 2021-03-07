(ns maze-project.interface.ui-canvas
  (:use [seesaw.core])
  (:require [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.reverse-backtracking :refer [reverse-backtracking]]
            [maze-project.algorithms.aldous-broder :refer [aldous-broder]]
            [maze-project.algorithms.binary-tree :refer [binary-tree]]
            [maze-project.interface.ui-grid :refer [draw-grid]]))

(defn get-maze-canvas [frame] (select frame [:#mazeCanvas]))

(def get-maze-names ["Binary Tree" "Aldous Broder" "Reverse Backtracking"])

(defn create-maze [mazeName rows cols]
  (let [grid (create-grid rows cols)]
    (cond
      (= mazeName (get-maze-names 0)) (binary-tree grid)
      (= mazeName (get-maze-names 1)) (aldous-broder grid)
      (= mazeName (get-maze-names 2)) (reverse-backtracking grid))))

; Might have to take this out when solving comes into play as it will need more layers on the graphics draw func
(defn draw-maze [frame mazeName rows cols]
  (let [maze (create-maze mazeName rows cols)
        canvas (get-maze-canvas frame)]
    (-> canvas (config! :paint #(draw-grid maze %1 %2)))))

(defn create-canvas []
  (canvas
    :id :mazeCanvas
    :background (color 240 240 240)
    :paint (fn [c g] (draw-grid maze c g))))