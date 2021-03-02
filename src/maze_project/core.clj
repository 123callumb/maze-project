(ns maze-project.core
  (:use [seesaw.core]
        [seesaw.graphics]
        [seesaw.color])
  (:require [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.binary-tree :refer [binary-tree]]
            [maze-project.interfaces.ui-grid :refer [draw-grid]]))

(defn create-maze []
   (let [grid (create-grid 5 5)
         grid-bt (binary-tree grid)]
     grid-bt))

(defn content []
  (let [big-label (label :text "This is a label omg")
        maze (create-maze)
        mazeCanvas (canvas :id :canvas :background (color 40 40 40) :paint (fn [c g] (draw-grid maze c g)))
        big-button (button :text "Draw")
        panel (border-panel :hgap 5 :vgap 5 :border 5
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
