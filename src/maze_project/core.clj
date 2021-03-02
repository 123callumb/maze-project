(ns maze-project.core
  (:use seesaw.core)
  (:use seesaw.graphics)
  (:require [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.binary-tree :refer [binary-tree]]
            [seesaw.selector :as selector]))

(defn create-maze []
   (let [grid (create-grid 5 5)
         grid-bt (binary-tree grid)]
     grid-bt))

(defn draw-on [])


(defn content []
  (let [big-label (label :text "This is a label omg")
        big-button (button :text "hey its me a button")
        panel (border-panel :hgap 5 :vgap 5 :border 5
                            :center (canvas :id :canvas :background "#0D1117" :paint nil)
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
