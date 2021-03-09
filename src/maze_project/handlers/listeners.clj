(ns maze-project.handlers.listeners
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [draw-maze]]
            [maze-project.handlers.file-handler :refer [save-maze load-maze]]
            [maze-project.models.maze :refer [create-and-set-maze]]))

(defn get-slider-val [frame sliderID sliderLabelID]
  (let [slider (select frame [sliderID])
        sliderCounter (select frame [sliderLabelID])
        sliderVal (selection slider)]
    (config! sliderCounter :text sliderVal)
    sliderVal))

(defn get-maze-settings [frame]
  {:rows (get-slider-val frame :#rowSlider :#rowCountLabel)
   :cols (get-slider-val frame :#colSlider :#colCountLabel)
   :alg (selection (select frame [:#mazeComboSel]))})

(defn generate-maze [e]
  (let [frame (to-frame e)
        opt (get-maze-settings frame)]
    (create-and-set-maze (:rows opt) (:cols opt) (:alg opt))
    (draw-maze frame false)))

(defn on-save-btn [e] (save-maze))
(defn on-load-btn [e] [(load-maze) (draw-maze (to-frame e) false)])
(defn on-solve [e] (draw-maze (to-frame e) true))

(defn register-listeners [l]
  (listen (select l [:#saveBtn]) :mouse-clicked on-save-btn)
  (listen (select l [:#loadBtn]) :mouse-clicked on-load-btn)
  (listen (select l [:#generateBtn]) :mouse-clicked generate-maze)
  (listen (select l [:#solveBtn]) :mouse-clicked on-solve))