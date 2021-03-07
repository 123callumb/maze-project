(ns maze-project.interface.ui-layout
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [create-canvas get-maze-names]]))

(defn create-left-panel []
  (let [mazeComboLabel (label :text "Set maze generation algorithm:")
        mazeComboBox (combobox :id :mazeComboSel :model get-maze-names)]
    (grid-panel
      :border "Properties"
      :columns 1
      :items [mazeComboLabel mazeComboBox])))

(defn create-layout [] (border-panel :west (create-left-panel) :center (create-canvas)))