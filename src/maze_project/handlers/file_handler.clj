(ns maze-project.handlers.file-handler
  (:require [maze-project.models.maze :refer [get-maze set-maze]])
  (:import (javax.swing JFileChooser)))

(defn get-file [toSave]
  (let [fileChooser (JFileChooser. "C:/")
        retVal (if (toSave) (.showSaveDialog fileChooser nil) (.showOpenDialog fileChooser nil))]
    (if (= retVal JFileChooser/APPROVE_OPTION)
      (.getAbsolutePath (.getSelectedFile fileChooser)))))

(defn save-maze []
  (let [saveLoc (get-file true)
        mazeToSave (get-maze)]))

(defn load-maze [])