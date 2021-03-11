(ns maze-project.handlers.file-handler
  (:require [maze-project.models.maze :refer [get-maze set-maze]]
            [maze-project.models.cell :refer [print-cell-to-str get-cell-from-str]]
            [clojure.string :as str])
  (:import (javax.swing JFileChooser)
           (javax.swing.filechooser FileNameExtensionFilter)
           (maze_project.models.maze MazeGrid)
           (maze_project.models.cell CellPos)))

(def fileExt "txt")

(defn get-file [toSave]
  (let [fileChooser (JFileChooser.)
        supportedExt (FileNameExtensionFilter. (str "." fileExt) (into-array [fileExt]))
        filter (.setFileFilter fileChooser supportedExt)
        retVal (if toSave (.showSaveDialog fileChooser nil) (.showOpenDialog fileChooser nil))]
    (if (= retVal JFileChooser/APPROVE_OPTION)
      (.getAbsolutePath (.getSelectedFile fileChooser)))))

(defn save-maze []
  (let [fileName (get-file true)
        maze (get-maze)
        startPos (:startPos maze)
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

(defn load-maze []
  (let [fileName (get-file false)
        file (slurp fileName)
        lines (str/split file #"\n")
        rows (Integer/parseInt (lines 0))
        cols (Integer/parseInt  (lines 1))
        startPosLn (str/split (lines 2) #",")
        endPosLn (str/split (lines 3) #",")
        cellsLn (lines 4)
        gridCells (str/split cellsLn #"\|")
        startPos (CellPos. (Integer/parseInt  (startPosLn 0)) (Integer/parseInt (startPosLn 1)))
        endPos (CellPos. (Integer/parseInt (endPosLn 0)) (Integer/parseInt (endPosLn 1)))
        gridSize (count gridCells)]
    (loop [row 0 col 0 index 0 grid [] currentRow []]
      (if (= index gridSize)
       (let [loadedMaze (MazeGrid. (conj grid currentRow) startPos endPos)]
         (set-maze loadedMaze)
         nil)
       (if (= col cols)
         (recur (inc row) 0 index (conj grid currentRow) [])
         (let [cell (get-cell-from-str (gridCells index))
               updatedRow (conj currentRow cell)]
           (recur row (inc col) (inc index) grid updatedRow)))))))