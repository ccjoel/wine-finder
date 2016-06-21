(ns wines.cli-test
  (:require [clojure.test :refer :all]
            [clojure.edn :as edn]
            [clojure.string :as s]
            [wines.cli :refer :all]
            [wines.util :refer [parse-json handle-program-error]]))

(def mocked-cli-args {:options {},
                      :arguments [],
                      :summary   "  -s, --search QUERY  Query string from cli\n  -h, --help          Prints help. GUI will start if no cli args passed.",
                      :errors nil})

(deftest parse-cli-args-test
  ; todo: rewrite this with a spy... don't want to open gui each time test runs...
;;   (testing "handles empty options"
;;     (with-redefs [println #(str %)]
;;       (is (nil? (parse-cli-args mocked-cli-args)))))

  (testing "handles help option"
    (with-redefs [println (fn [& args] (s/join args))]
      ; returns "", but prints help
      (is (= "" (parse-cli-args {:options {:help true}}))))))


; todo: change to use resource instead of abs path.. check which is better
(def mocked-body (slurp (clojure.java.io/resource "body.seed.json")))
(def mocked-meta (edn/read-string (slurp (clojure.java.io/resource "http.seed.edn"))))


(deftest handle-api-body-test

  (testing "Handles GOOD 200 api response"
    (is (=
         {:Id 145612,
           :Name "Rombauer Chardonnay 2014",
           :Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx",
           :PriceRetail 36.0,
           :Description ""}
         (first (handle-api-body (:status mocked-meta) (:headers mocked-meta) mocked-body))
         )))

  (testing "Handles non-ok api status codes"
    (is (nil?
         (handle-api-body 400 (:headers mocked-meta) mocked-body))))

  (testing "Handles 200 but non-zero api ReturnCode (error)"
    (is (= "[\"Errored\"]"
           (with-redefs [handle-program-error #(-> %)]
             (handle-api-body (:status mocked-meta)
                                  (:headers mocked-meta)
                                  "{\"Status\": {\"Messages\": [\"Errored\"], \"ReturnCode\": 10}}"
                                  )))))

  (testing "Handles 200 empty api response Products"
    (is (= "Expected Products to contain a List of products."
           (with-redefs [handle-program-error #(-> %)]
             (handle-api-body (:status mocked-meta)
                                  (:headers mocked-meta)
                                  "{\"Status\": {\"Messages\": [], \"ReturnCode\": 0}, \"Products\": {}}"
                                  )))))

  (testing "Handles 200 malformatted api response"
    (is (= "Expected Products to contain a List of products."
           (with-redefs [handle-program-error #(-> %)]
             (handle-api-body (:status mocked-meta)
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
