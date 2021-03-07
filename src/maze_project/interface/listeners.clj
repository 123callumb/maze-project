(ns maze-project.interface.listeners
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [draw-maze]]))

(defn get-row-slider-val [frame]
  (selection (select frame [:#rowSlider])))

(defn get-col-slider-val [frame]
  (selection (select frame [:#colSlider])))

(defn get-maze-alg-name [frame]
  (selection (select frame [:#mazeComboSel])))

(defn update-maze-draw [e]
  (let [frame (to-frame e)
        mazeAlgName (get-maze-alg-name frame)
        rows (get-row-slider-val frame)
        cols (get-col-slider-val frame)]
    (draw-maze frame mazeAlgName rows cols)))

(defn register-listeners [l]
  (listen (select l [:#mazeComboSel]) :selection update-maze-draw)
  (listen (select l [:#rowSlider]) :change update-maze-draw)
  (listen (select l [:#colSlider]) :change update-maze-draw))


