(ns maze-project.core
  (:use seesaw.core))

(defn content []
  (let [big-label (label :text "This is a label omg") big-button (button :text "hey its me a button") panel (flow-panel :border 5 :items [big-label big-button])]
    panel))

(defn -main [& args]
  (invoke-later
    (-> (frame :title "\"aMazing\" - Chris Bates 02/03/2021" :content (content) :on-close :exit)
        pack!
        show!)))
