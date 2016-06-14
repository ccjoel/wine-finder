(ns wines.http
  (:require
   [org.httpkit.client :as http]
   [wines.util :refer [parse-json format-query-params handle-program-error]]
   [wines.constants :refer [conf protocol api-path]]
   ))

(defn is-200? [status]
  (= status 200))

(defn handle-products-listing [products]
  (let [product-list (:List products) total (:Total products)]
    (if (= total 0)
      (handle-program-error "No products found with your criteria.")
      (if (not (nil? product-list))
        (for [product product-list]
          (when (not (nil? product))
            ; todo: printlns from the promise not shown when running standalone
            (select-keys product [:Id :Name :Url :PriceRetail :Description])
            ))
        (handle-program-error "Expected Products to contain a List of products."))
      ))
  )

(defn handle-api-response [status headers body]
  (when (is-200? status)
    (let [json-res (parse-json body "Api response was not valid json.")
          status (:Status json-res)]
      (if (= (:ReturnCode status) 0)
        (let [products (:Products json-res)]
          (if (not (nil? products))
            (handle-products-listing products)
            (handle-program-error "Expected products on json payload, but got nil.")))
        (handle-program-error (str (:Messages status)))))))

(defn api-call [api-resource query-params]
  (http/get
   (str protocol "://" api-path api-resource
        "?apikey=" (:api_key conf)
        (format-query-params query-params)) ;can pass options to GET
   (fn [{:keys [status headers body error]}] ;; asynchronous response handling
     (if error
       (println "Failed, exception is " error)
       (do
         ; todo: printlns from the promise not shown when running standalone (futures? )
         (println "Async HTTP GET: " status)
         (println "headers: " headers)
         (handle-api-response status headers body))))))
