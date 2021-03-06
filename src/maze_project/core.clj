(ns maze-project.core
  (:use [seesaw.core]
        [seesaw.graphics]
        [seesaw.color])
  (:require [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.binary-tree :refer [binary-tree]]
            [maze-project.interfaces.ui-grid :refer [draw-grid]]))

(defn create-maze []
   (let [grid (create-grid 20 20)
         grid-bt (binary-tree grid)]
     (println grid)
     grid-bt))

(defn content []
  (let [big-label (label :text "This is a label omg")
        maze (create-maze)
        mazeCanvas (canvas :id :canvas :background (color 240 240 240) :paint (fn [c g] (draw-grid maze c g)))
        big-button (button :text "Draw")
        panel (border-panel
                            :center mazeCanvas
                            :south (horizontal-panel :items [big-label big-button]))]
    panel))

(defn -main [& args]
  (invoke-later
    (-> (frame
          :title "\"aMazing\" - Chris Bates 02/03/2021"
          :width 640
          :height 500
          :minimum-size [640 :by 500]
          :content (content)
          :on-close :exit)
        pack!
        show!)))
