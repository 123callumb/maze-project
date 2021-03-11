(ns maze-project.interface.ui-layout
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [create-canvas]]
            [maze-project.models.maze :refer [get-maze-names]]
            [maze-project.models.grid :refer [get-grid-shape-names]]))

(defn create-left-panel []
  (let [saveMazeButton (button :id :saveBtn :text "Save Maze")
        loadMazeButton (button :id :loadBtn :text "Load Maze")
        mazeComboLabel (label :text "Set maze generation algorithm:")
        mazeComboBox (combobox :id :mazeComboSel :model get-maze-names)
        mazeShapeBox (combobox :id :mazeShapeSel :model get-grid-shape-names)
        rowSliderLabel (label :text "Maze Rows:")
        rowSlider (slider :id :rowSlider :min 3 :max 50 :value 20 :orientation :horizontal :minor-tick-spacing 1 :major-tick-spacing 1 :snap-to-ticks? true)
        colSliderLabel (label :text "Maze Columns:")
        colSlider (slider :id :colSlider :min 3 :max 50 :value 20 :orientation :horizontal :minor-tick-spacing 1 :major-tick-spacing 1 :snap-to-ticks? true)
        solveMazeButton (button :id :solveBtn :text "Solve Maze")
        generateMazeButton (button :id :generateBtn :text "Generate Maze")]
    (grid-panel
      :border "Maze Options"
      :columns 1
      :items [saveMazeButton
              loadMazeButton
              mazeComboLabel
              mazeComboBox
              mazeShapeBox
              rowSliderLabel
              rowSlider
              colSliderLabel
              colSlider
              solveMazeButton
              generateMazeButton])))

(defn create-bottom-panel []
  (let [rowCountPrefix (label :text "Rows: ")
        rowCountLabel (label :id :rowCountLabel :text 20)
        colCountPrefix (label :text "Cols: ")
        colCountLabel (label :id :colCountLabel :text  20)]
    (flow-panel :items [rowCountPrefix rowCountLabel colCountPrefix colCountLabel])))

(defn create-layout [] (border-panel
                         :west (create-left-panel)
                         :center (create-canvas)
                         :south (create-bottom-panel)))