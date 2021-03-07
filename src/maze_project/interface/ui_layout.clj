(ns maze-project.interface.ui-layout
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-canvas :refer [create-canvas get-maze-names]]))

(defn create-left-panel []
  (let [mazeComboLabel (label :text "Set maze generation algorithm:")
        mazeComboBox (combobox :id :mazeComboSel :model (get-maze-names))]
    (form-panel
      :border 10
      :items [mazeComboLabel mazeComboBox])))

(defn create-layout []
  (let [(border-panel
          :west create-left-panel
          :center mazeCanvas)]
    panel))
