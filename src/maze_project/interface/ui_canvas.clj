(ns maze-project.interface.ui-canvas
  (:use [seesaw.core]
        [seesaw.color]
        [seesaw.graphics])
  (:require [maze-project.interface.ui-grid :refer [draw-grid draw-journey]]
            [maze-project.models.maze :refer [get-maze]]
            [maze-project.algorithms.maze-solving.depth-first :refer [depth-first]]))

; Grab the ui canvas where the maze is drawn
(defn get-maze-canvas [frame] (select frame [:#mazeCanvas]))

; Draw the maze on the canvas, also the ability to pass a boolean
; in to calculate the solution and draw it here as well.
(defn draw-maze [frame showSolution]
  (let [maze (get-maze)
        rows (count (:grid maze)) ;temp for now
        cols  (count ((:grid maze) 0))
        canvas (get-maze-canvas frame)]
    (config! canvas :paint (fn[c g]
                             (if showSolution (draw-journey g c rows cols (depth-first maze)))
                             (draw-grid maze c g)))))

(defn create-canvas []
  (canvas :id :mazeCanvas :background (color "#00d6b9") :paint nil :border 0))