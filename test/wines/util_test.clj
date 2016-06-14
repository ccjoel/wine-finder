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
    (with-redefs [exit-now! #(str "A MSG")
                  println #(str %)]
      (is (=
           (parse-json "{a: \"world\"}" "A MSG")
           "A MSG")))))


(deftest format-query-params-test

  (testing "formats query params appropiatedly"
    (is (= (format-query-params {:search "query", :hi 5}) "&search=query&hi=5")))

  (testing "handles nil for format query params"
    (is (= (format-query-params {}) ""))))
