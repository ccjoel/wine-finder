(ns wines.util-test
  (:require [clojure.test :refer :all]
            [wines.util :refer :all]))


(deftest with-abs-path-test
  (testing "Returns correct abs path"
    (is (.contains (with-abs-path "FILE") "wines/FILE"))))


(deftest parse-json-test
  (testing "Parses GOOD json appropriatedly"
    (is (= (parse-json "{\"hello\": \"world\"}" "ERROR MSG") {:hello "world"})))

  (testing "Handles parsing BAD json appropriatedly"
    (with-redefs [exit-now! #(str "A MSG")]
      (is (=
           (parse-json "{a: \"world\"}" "A MSG")
           "A MSG")))))
