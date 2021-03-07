(ns maze-project.core
  (:use [seesaw.core]
        [seesaw.graphics]
        [seesaw.color])
  (:require [maze-project.interface.ui-layout :refer [create-layout]]))

(defn create-content []
  (let [layout (create-layout)]
       (listen (select layout [:#mazeComboSel]) :selection (fn [e] (println "Selection is " (selection e))))))

(defn -main [& args]
  (invoke-later
    (-> (frame
          :title "\"aMazing\" - Chris Bates 02/03/2021"
          :width 640
          :height 500
          :minimum-size [640 :by 500]
          :content (create-content)
          :on-close :exit)
        pack!
        show!)))
