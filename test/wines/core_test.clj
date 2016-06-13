(ns wines.core-test
  (:require [clojure.test :refer :all]
            [clojure.edn :as edn]
            [wines.core :refer :all]
            [wines.util :refer [with-abs-path parse-json]]))

(def mocked-body (slurp (with-abs-path "dev-resources/body.seed.json")))
(def mocked-meta (edn/read-string (slurp (with-abs-path "dev-resources/http.seed.edn"))))

(deftest handle-api-response-test
  (testing "Handles GOOD api response"
    (is (=
         (handle-api-response (:status mocked-meta) (:headers mocked-meta) mocked-body)
         {:Messages [], :ReturnCode 0}
         )))
  (testing "Handles BAD api response"
    (is (nil?
         (handle-api-response 400 (:headers mocked-meta) mocked-body)))))
