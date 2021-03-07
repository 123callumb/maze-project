(ns maze-project.interface.ui-layout
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [create-canvas get-maze-names]]))

(defn create-left-panel []
  (let [mazeComboLabel (label :text "Set maze generation algorithm:")
        mazeComboBox (combobox :id :mazeComboSel :model get-maze-names)
        rowSliderLabel (label :text "Maze Rows:")
        rowSlider (slider :id :rowSlider :min 3 :max 50 :value 20 :orientation :horizontal :minor-tick-spacing 1 :major-tick-spacing 1 :snap-to-ticks? true)
        colSliderLabel (label :text "Maze Columns:")
        colSlider (slider :id :colSlider :min 3 :max 50 :value 20 :orientation :horizontal :minor-tick-spacing 1 :major-tick-spacing 1 :snap-to-ticks? true)]
    (grid-panel
      :border "Properties"
      :columns 1
      :items [mazeComboLabel mazeComboBox rowSliderLabel rowSlider colSliderLabel colSlider])))

(defn create-layout [] (border-panel :west (create-left-panel) :center (create-canvas)))