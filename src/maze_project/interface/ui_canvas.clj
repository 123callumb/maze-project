(ns maze-project.interface.ui-canvas
  (:use [seesaw.core]
        [seesaw.color])
  (:require [maze-project.interface.ui-grid :refer [draw-grid draw-journey]]
            [maze-project.models.maze :refer [get-maze]]
            [maze-project.algorithms.maze-solving.depth-first :refer [depth-first]]))


(defn get-maze-canvas [frame] (select frame [:#mazeCanvas]))

; Might have to take this out when solving comes into play as it will need more layers on the graphics draw func
(defn draw-maze [frame]
  (let [maze (get-maze)
        rows (count (:grid maze)) ;temp for now
        cols  (count ((:grid maze) 0))
        canvas (get-maze-canvas frame)
        journey (depth-first maze)]
    (config! canvas :paint (fn[c g] [(draw-journey g c rows cols journey) (draw-grid maze c g)]))))

(defn create-canvas []
  (canvas :id :mazeCanvas :background (color "#00d6b9") :paint nil :border 0))