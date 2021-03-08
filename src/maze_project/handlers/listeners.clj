(ns maze-project.handlers.listeners
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [draw-maze]]))

(defn get-slider-val [frame sliderID sliderLabelID]
  (let [slider (select frame [sliderID])
        sliderCounter (select frame [sliderLabelID])
        sliderVal (selection slider)]
    (config! sliderCounter :text sliderVal)
    sliderVal))

(defn get-maze-alg-name [frame]
  (selection (select frame [:#mazeComboSel])))

(defn update-maze-draw [e]
  (let [frame (to-frame e)
        mazeAlgName (get-maze-alg-name frame)
        rows (get-slider-val frame :#rowSlider :#rowCountLabel)
        cols (get-slider-val frame :#colSlider :#colCountLabel)]
    (draw-maze frame mazeAlgName rows cols)))

(defn register-listeners [l]
  (listen (select l [:#mazeComboSel]) :selection update-maze-draw)
  (listen (select l [:#rowSlider]) :change update-maze-draw)
  (listen (select l [:#colSlider]) :change update-maze-draw))


