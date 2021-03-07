(ns maze-project.interface.listeners
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [draw-maze]]))

(defn on-maze-combo [e]
  (let [mazeName (selection e)
        frame (to-frame e)]
    (draw-maze frame mazeName 20 20)))

(defn register-listeners [l]
  (listen (select l [:#mazeComboSel]) :selection on-maze-combo))


