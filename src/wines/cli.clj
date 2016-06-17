(ns wines.cli
  (:require
    [clojure.pprint :refer [pprint]]
    [gui.core :refer [call-ui]]
    [wines.util :refer [parse-json format-query-params handle-program-error]]
    [wines.http :refer [api-call]]))

;; ---------- TODO: remove this section
; hardcoding resource to catalog for now
(def api-resource "catalog")

; need to set as default api param if search is on (basically if not using gui...)

;; ----------- END TODO

(defn print-cli-headers [help]
  (println "Welcome Wine Lovers.")
  (when help
    (println "Options available:")))

(defn handle-products-listing [products]
  (let [product-list (:List products) total (:Total products)]
    (if (= total 0)
      (handle-program-error "No products found with your criteria.")
      (if-not (nil? product-list)
        (for [product product-list]
          (when (not (nil? product))
            (select-keys product [:Id :Name :Url :PriceRetail :Description])))
        (handle-program-error "Expected Products to contain a List of products.")))))

(defn handle-api-body [status headers body]
  (when (= status 200)
    (let [json-res (parse-json body "Api response was not valid json.")
          status (:Status json-res)]
      (if (= (:ReturnCode status) 0)
        (if-let [products (:Products json-res)]
          (handle-products-listing products)
          (handle-program-error "Expected products on json payload, but got nil."))
        (handle-program-error (str (:Messages status)))))))

(defn parse-cli-args [cli-args]
  (if (:errors cli-args)
    (do
      (println (:summary cli-args))
      (println (:errors cli-args))
      (handle-program-error "Wrong arguments provided."))
    (let [opts (:options cli-args)]
      (cond
        (empty? opts) (call-ui)
        ; todo, change "not nil" to just checking if val exists?
        (not (nil?
               (:help opts))) (do
                                (print-cli-headers true)
                                (println (:summary cli-args)))
        (not
          (nil?
            (:search opts))) (pprint
                               (doall
                                 @(api-call api-resource opts
                                           (fn [status headers body]
                                             (println "Async HTTP GET status: " status)
                                             (println "Headers: ")
                                             (pprint headers)
                                             (println "Response body...")
                                             (handle-api-body status headers body))
                                           (fn [error]
                                             (println "Failed, exception is " error)))))))))
