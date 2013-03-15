(ns fw.matching-test
  (:use [clojure.test]
        [fw.matching :only [matching]]))

(deftest matching-test
  (testing "matching"
    (testing "map destructuring"
      (testing "uses literal-style maps instead of the value-key maps that let uses"
        (matching [{:x x} {:x 1}]
                  (is (= x 1))))
      (testing "is recursive"
        (matching [{:x {:y x}} {:x {:y 1}}]
                  (is (= x 1))))
      (testing "inside of other structures still works like matching, not let"
        (matching [[{:x x}] [{:x 1}]]
                  (is (= x 1)))))

    (testing "no regressions from let"
      (testing "simple local assignment still works"
        (matching [x 1]
                  (is (= x 1))))
      (testing "vector assignment still works"
        (matching [[x] [1]]
                  (is (= x 1))))
      (testing "multiple assignments still work"
        (matching [x 1
                   y 2]
                  (is (= x 1))
                  (is (= y 2)))))))