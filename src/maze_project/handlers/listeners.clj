(ns maze-project.handlers.listeners
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [draw-maze]]
            [maze-project.handlers.file-handler :refer [save-maze load-maze]]
            [maze-project.models.maze :refer [create-and-set-maze alg-name-binary-tree get-maze]]))

; Get current slider value and update the info at the bottom
; of the layout
(defn get-slider-val [frame sliderID sliderLabelID]
  (let [slider (select frame [sliderID])
        sliderCounter (select frame [sliderLabelID])
        sliderVal (selection slider)]
    (config! sliderCounter :text sliderVal)
    sliderVal))

; Get current option values from ui
(defn get-maze-settings [frame]
  {:rows (get-slider-val frame :#rowSlider :#rowCountLabel)
   :cols (get-slider-val frame :#colSlider :#colCountLabel)
   :alg (selection (select frame [:#mazeComboSel]))
   :shape (selection (select frame [:#mazeShapeSel]))})

; Generate maze callback, calls create maze based on whatever maze options
; are currently set
(defn generate-maze [e]
  (let [frame (to-frame e)
        opt (get-maze-settings frame)]
    (create-and-set-maze (:rows opt) (:cols opt) (:alg opt) (:shape opt))
    (draw-maze frame false)))

; Make sure when binary tree is selected the shape cannot be defined.
(defn on-maze-alg-change [e]
  (let [frame (to-frame e)
        selectedAlg (selection e)
        shapeSelect (select frame [:#mazeShapeSel])]
    (if (= selectedAlg alg-name-binary-tree)
      [(selection! shapeSelect "Rectangle")
       (config! shapeSelect :enabled? false)]
      (config! shapeSelect :enabled? true))))


; Callbacks for the remaining option buttons
(defn on-save-btn [e] (save-maze))
(defn on-load-btn [e] [(load-maze) (draw-maze (to-frame e) false)])
(defn on-solve [e] (draw-maze (to-frame e) true))

; Register every listener
(defn register-listeners [l]
  (listen (select l [:#saveBtn]) :mouse-clicked on-save-btn)
  (listen (select l [:#loadBtn]) :mouse-clicked on-load-btn)
  (listen (select l [:#generateBtn]) :mouse-clicked generate-maze)
  (listen (select l [:#solveBtn]) :mouse-clicked on-solve)
  (listen (select l [:#mazeComboSel]) :selection on-maze-alg-change))