(ns maze-project.models-tests.grid-tests
  (:require [clojure.test :refer :all]
            [maze-project.models.grid :refer :all]
            [maze-project.models.cell :refer [create-cell]]))

(deftest pos-is-in-ellipse-tests
  (testing "position in ellipse"
    (is (pos-is-in-ellipse 14 14 5 6)))
  (testing "position outside of ellipse"
    (let [result (pos-is-in-ellipse 14 14 15 2)
          expectedResult false]
      (is (= expectedResult result)))))

(deftest create-grid-tests
  (testing "All cols and rows created for rect grid"
    (let [rows 10
          cols 12
          result (create-rect-grid rows cols)]
      (is (and (= (count result) rows) (= (count (result 0)) cols)))))
  (testing "All cols and rows created for ellipse grid"
    (let [rows 14
          cols 16
          result (create-ellipse-grid rows cols)]
      (is (and (= (count result) rows) (= (count (result 0)) cols))))))

(deftest get-rnd-grid-pos-tests
  (testing "Pos found in regular grid"
    (let [cols 10
          rows 10
          grid (create-rect-grid cols rows)
          result (get-rnd-grid-pos grid)
          rowResult (:row result)
          colResult (:col result)]
      (is (and (< rowResult rows) (< colResult cols)))))
  (testing "Pos found in grid with ignore cells"
    (let [cols 12
          rows 12
          grid-ellipse (create-ellipse-grid cols rows)
          result (get-rnd-grid-pos grid-ellipse)
          rowResult (:row result)
          colResult (:col result)
          cellChosen ((grid-ellipse rowResult) colResult)]
      (is (and (< rowResult rows) (< colResult cols) (not (:ignore cellChosen)))))))

(deftest get-valid-cell-count-tests
  (testing "get valid cell count of grid with no ignores"
    (let [cols 6
          rows 8
          grid (create-rect-grid rows cols)
          expectedResult (* cols rows)
          result (get-valid-cell-count grid)]
      (is (= result expectedResult))))
  (testing "get calid cell count of grid with ignore cells"
    (let [cols 8
          rows 10
          grid (create-rect-grid rows cols)
          ignoreLayer (vec (repeat cols (create-cell 0 0 0 0 true)))
          fullGrid (conj grid ignoreLayer)
          expectedResult (* cols rows)
          result (get-valid-cell-count fullGrid)]
      (is (= result expectedResult)))))