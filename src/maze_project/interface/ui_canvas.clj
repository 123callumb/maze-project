(ns maze-project.interface.ui-canvas
  (:use [seesaw.core]
        [seesaw.color])
  (:require [maze-project.interface.ui-grid :refer [draw-grid draw-journey]]
            [maze-project.models.maze :refer [get-maze]]
            [maze-project.algorithms.maze-solving.depth-first :refer [depth-first]]))

(defn get-maze-canvas [frame] (select frame [:#mazeCanvas]))

(defn draw-maze [frame showSolution]
  (let [maze (get-maze)
        rows (count (:grid maze)) ;temp for now
        cols  (count ((:grid maze) 0))
        canvas (get-maze-canvas frame)]
    (config! canvas :paint (fn[c g] [ (if showSolution (draw-journey g c rows cols (depth-first maze))) (draw-grid maze c g)]))))

(defn create-canvas []
  (canvas :id :mazeCanvas :background (color "#00d6b9") :paint nil :border 0))