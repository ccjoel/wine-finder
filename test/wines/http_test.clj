(ns wines.http-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [clojure.edn :as edn]
            [wines.http :refer :all]
            [wines.util :refer [with-abs-path parse-json handle-program-error]]))

(def mocked-body (slurp (with-abs-path "dev-resources/body.seed.json")))
(def mocked-meta (edn/read-string (slurp (with-abs-path "dev-resources/http.seed.edn"))))

(deftest handle-api-response-test

  (testing "Handles GOOD 200 api response"
    (is (=
         {:Id 145612,
           :Name "Rombauer Chardonnay 2014",
           :Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx",
           :PriceRetail 36.0,
           :Description ""}
         (first (handle-api-response (:status mocked-meta) (:headers mocked-meta) mocked-body))
         )))

  (testing "Handles non-ok api status codes"
    (is (nil?
         (handle-api-response 400 (:headers mocked-meta) mocked-body))))

  (testing "Handles 200 but non-zero api ReturnCode (error)"
    (is (= "[\"Errored\"]"
           (with-redefs [handle-program-error #(-> %)]
             (handle-api-response (:status mocked-meta)
                                  (:headers mocked-meta)
                                  "{\"Status\": {\"Messages\": [\"Errored\"], \"ReturnCode\": 10}}"
                                  )))))

  (testing "Handles 200 empty api response Products"
    (is (= "Expected Products to contain a List of products."
           (with-redefs [handle-program-error #(-> %)]
             (handle-api-response (:status mocked-meta)
                                  (:headers mocked-meta)
                                  "{\"Status\": {\"Messages\": [], \"ReturnCode\": 0}, \"Products\": {}}"
                                  )))))

  (testing "Handles 200 malformatted api response"
    (is (= "Expected Products to contain a List of products."
           (with-redefs [handle-program-error #(-> %)]
             (handle-api-response (:status mocked-meta)
                                  (:headers mocked-meta)
                                  "{\"Status\": {\"Messages\": [], \"ReturnCode\": 0}, \"Products\": 0}"
                                  )))))

;;   (testing "Handles 200 malformatted api response"
;;     (is (= "Expected Products ist to contain products."
;;            (with-redefs [handle-program-error #(-> %)]
;;              (handle-api-response (:status mocked-meta)
;;                                   (:headers mocked-meta)
;;                                   "{\"Status\": {\"Messages\": [], \"ReturnCode\": 0}, \"Products\": \"List\": []}"
;;                                   )))))

  )
