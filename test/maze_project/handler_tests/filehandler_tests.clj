(ns maze-project.handler-tests.filehandler-tests
  (:require [clojure.test :refer :all]
            [maze-project.handlers.file-handler :refer :all]
            [maze-project.models.maze :refer [create-and-set-maze alg-name-reverse-backtrack maze-exists]]))

; These tests are a bit odd but its a way of checking to see
; if the file saving and loading is functional outside of using
; the ui
(deftest save-test
  (testing "Testing saving maze"
    (create-and-set-maze 10 10 alg-name-reverse-backtrack "Ellipse")
    (save-maze)))

(deftest load-maze-test
  (testing "Make sure maze loads and sets"
    (load-maze)
    (is (maze-exists))))