(ns maze-project.maze-algorithm-tests.maze-creation-tests.binary-tree-tests
  (:require [clojure.test :refer :all]
            [maze-project.algorithms.maze-creation.binary-tree :refer :all]
            [maze-project.models.maze :refer [alg-name-binary-tree create-and-set-maze get-maze]]
            [maze-project.maze-algorithm-tests.maze-creation-tests.maze-test-helper :refer [maze-is-fully-linked]]))

(deftest binary-tree-tests
  (testing "Testing to see if a rectangular maze is fully connected"
    (create-and-set-maze 10 8 alg-name-binary-tree "Rectangle")
    (is (maze-is-fully-linked (:grid (get-maze))))))