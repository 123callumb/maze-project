(ns maze-project.handlers.file-handler
  (:require [maze-project.models.maze :refer [get-maze set-maze]]
            [maze-project.models.cell :refer [print-cell-to-str get-cell-from-str]]
            [clojure.string :as str])
  (:import (javax.swing JFileChooser)
           (javax.swing.filechooser FileNameExtensionFilter)
           (maze_project.models.cell CellPos)
           (maze_project.models.grid MazeGrid)))

(def fileExt "mze")

(defn get-file [toSave]
  (let [fileChooser (JFileChooser.)
        supportedExt (FileNameExtensionFilter. (str "." fileExt) (into-array [fileExt]))
        filter (.setFileFilter fileChooser supportedExt)
        retVal (if toSave (.showSaveDialog fileChooser nil) (.showOpenDialog fileChooser nil))]
    (if (= retVal JFileChooser/APPROVE_OPTION)
      (.getAbsolutePath (.getSelectedFile fileChooser)))))

(defn write-maze-to-file [fileName maze]
  (let [startPos (:startPos maze)
        endPos (:endPos maze)
        startPosStr (str (:row startPos) "," (:col startPos))
        endPosStr (str (:row endPos) "," (:col endPos))
        mazeGrid (:grid maze)
        rows (count mazeGrid)
        cols (count (mazeGrid 0))
        flatGrid (flatten mazeGrid)
        mazeCellsString (reduce #(str %1 (print-cell-to-str %2)) "" flatGrid)
        finalStr (str rows "\n" cols "\n" startPosStr "\n" endPosStr "\n" mazeCellsString)
        fullFilePath (str fileName "." fileExt)]
    (spit fullFilePath finalStr :append false)))

(defn save-maze []
  (let [saveLoc (get-file true)
        mazeToSave (get-maze)]
    (write-maze-to-file saveLoc mazeToSave)))

(defn load-maze [fileName]
  (let [file (slurp fileName)
        lines (str/split file #"\n")
        rows (lines 0)
        cols (lines 1)
        startPosLn (lines 2)
        endPosLn (lines 3)
        cellsLn (lines 4)
        gridCells (str/split cellsLn #"|")
        startPos (apply ->CellPos (str/split startPosLn #","))
        endPos (apply ->CellPos (str/split endPosLn #","))]
    (loop [row 0 col 0 index 0 grid [] currentRow []]
      (if (and (= row rows) (= col cols))
        (MazeGrid. grid startPos endPos)
        (if (> col cols)
          (recur (inc row) 0 index (conj grid currentRow) [])
          (let [cell (get-cell-from-str (gridCells index))
                updatedRow (conj currentRow cell)]
            (recur row (inc col) (inc index) grid updatedRow)))))))