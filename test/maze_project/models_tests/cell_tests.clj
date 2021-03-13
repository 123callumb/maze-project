(ns maze-project.models-tests.cell-tests
  (:require [clojure.test :refer :all]
            [maze-project.models.cell :refer :all]))

(deftest get-cell-from-string-tests
  (testing "Exception thrown on incorrect string"
    (is (thrown? NumberFormatException (get-cell-from-str "incorrectstringformat"))))
  (testing  "Verify input/output"
    (let [result (get-cell-from-str "1,0,1,1,false")
          expectedResult (create-cell 1 0 1 1 false)]
      (is (= result expectedResult)))))

(deftest print-cell-to-str-tests
  (testing "Verify input/output"
    (let [result (print-cell-to-str (create-cell 1 1 0 1 true))
          expectedResult "1,1,0,1,true|"]
      (is (= result expectedResult)))))