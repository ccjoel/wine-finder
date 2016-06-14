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

(def mocked-cli-args {:options {},
                      :arguments [],
                      :summary   "  -s, --search QUERY  Query string from cli\n  -h, --help          Prints help. GUI will start if no cli args passed.",
                      :errors nil})

(deftest parse-cli-args-test
  (testing "handles empty options"
    (with-redefs [println #(str %)]
    (is (nil? (parse-cli-args mocked-cli-args)))))

  (testing "handles help option"
;;     (with-redefs [println #(str %)]
    (is (nil? (parse-cli-args {:options {:help true}})))))

;;   )

(deftest format-query-params-test
  (testing "formats query params appropiatedly"
    (is (= (format-query-params {:search "query", :hi 5}) "&search=query&hi=5")))

  (testing "handles nil for format query params"
    (is (= (format-query-params {}) "")))
  )
