(ns maze-project.maze-algorithm-tests.maze-creation-tests.aldous-broder-tests
  (:require [clojure.test :refer :all]
            [maze-project.algorithms.maze-creation.aldous-broder :refer :all]
            [maze-project.models.maze :refer [alg-name-aldous-broder create-and-set-maze get-maze]]))

(deftest get-next-rnd-cell-pos-tests
  (testing "making sure a cell outside of the maze is not returned upper bounds"
    (let [cols 10 rows 10]
      (create-and-set-maze rows cols alg-name-aldous-broder "Rectangle")
      (let [maze (get-maze)
            grid (:grid maze)
            row (dec rows)
            col (dec cols)
            resultPos (get-next-rnd-cell-pos row col rows cols grid)]
        (is (or (< (:nextRow resultPos) row) (< (:nextCol resultPos) col))))))
  (testing "making sure a cell outside of the maze is not returned lower bounds"
    (let [cols 10 rows 10]
      (create-and-set-maze rows cols alg-name-aldous-broder "Rectangle")
      (let [maze (get-maze)
            grid (:grid maze)
            row 0
            col 0
            resultPos (get-next-rnd-cell-pos row col rows cols grid)]
        (is (or (> (:nextRow resultPos) row) (> (:nextCol resultPos) col)))))))

(deftest aldous-broder-tests
  (testing ""))
