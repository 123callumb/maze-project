(ns maze-project.maze-algorithm-tests.maze-creation-tests.reverse-backtracking-tests
  (:require [clojure.test :refer :all]
            [maze-project.algorithms.maze-creation.reverse-backtracking :refer :all]
            [maze-project.models.grid :refer [create-grid]]
            [maze-project.algorithms.maze-creation.alg-helper :refer [break-walls]]
            [maze-project.models.maze :refer [alg-name-reverse-backtrack create-and-set-maze get-maze]]
            [maze-project.maze-algorithm-tests.maze-creation-tests.maze-test-helper :refer [maze-is-fully-linked]]))

; testing to make sure the helper function used inside of reverse back tracking
; helps get non visited cell neighbours correctly
(deftest get-non-visited-neighbours-test
  (testing "all neighbours are visited"
    (let [maxRow 10
          maxCol 10
          grid (create-grid maxRow maxCol "Rectangle")
          col 4
          row 4
          grid (break-walls grid row col (inc row) col)
          grid (break-walls grid row col (dec row) col)
          grid (break-walls grid row col row (inc col))
          grid (break-walls grid row col row (dec col))
          result (get-non-visited-neighbours grid row col maxRow maxCol)]
      (is (= (count result) 0))))
  (testing "No visited neighbours"
    (let [maxRow 10
          maxCol 10
          grid (create-grid maxRow maxCol "Rectangle")
          col 4
          row 4
          result (get-non-visited-neighbours grid row col maxRow maxCol)]
      (is (= (count result) 4)))))

; Use the maze test helper to make sure the maze is fully linked
(deftest reverse-backtracking-tests
  (testing "Make sure rectangular maze is fully connected"
    (create-and-set-maze 4 5 alg-name-reverse-backtrack "Rectangle")
    (is (maze-is-fully-linked (:grid (get-maze)))))
  (testing "Make sure ellipse maze is fully connected"
    (create-and-set-maze 7 5 alg-name-reverse-backtrack "Ellipse")
    (is (maze-is-fully-linked (:grid (get-maze))))))
