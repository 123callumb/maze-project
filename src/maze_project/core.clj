(ns maze-project.core
  (:use seesaw.core))

(defn content []
  "Hey look its me saying hello world for the 100th time in my life:)")

(defn -main [& args]
  (invoke-later
    (-> (frame :title "\"aMazing\" - Chris Bates 02/03/2021" :content (content) :on-close :exit)
        pack!
        show!)))
