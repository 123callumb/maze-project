(ns maze-project.models-tests.maze-tests
  (:require [clojure.test :refer :all]
            [maze-project.models.maze :refer :all]
            [maze-project.models.grid :refer [create-rect-grid]]
            [maze-project.algorithms.maze-creation.reverse-backtracking :refer [reverse-backtracking]]))

(deftest setting-and-getting-maze-tests
  (testing "After setting the maze it exists on the global state"
    (create-and-set-maze 10 10 alg-name-aldous-broder "Rectangle")
    (let [maze (get-maze)]
      (is (and (maze-exists) (not (nil? maze))))))
  (testing "manual setting maze and checking it exists on the global state"
    (let [grid (create-rect-grid 10 10)
          maze (reverse-backtracking grid)]
      (set-maze maze)
      (is (maze-exists)))))

