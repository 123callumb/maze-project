(ns maze-project.core
  (:use [seesaw.core])
  (:require [maze-project.interface.ui-layout :refer [create-layout]]
            [maze-project.handlers.listeners :refer [register-listeners]]
            [maze-project.interface.ui-canvas :refer [draw-maze]]
            [maze-project.models.maze :refer [create-and-set-maze]]))

(defn create-content []
  (let [layout (create-layout)]
       (register-listeners layout)
       ;(create-and-set-maze 20 20 "Binary Tree")
       ;(draw-maze layout false)
       layout))

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
