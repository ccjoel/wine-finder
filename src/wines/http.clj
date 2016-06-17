(ns wines.http
  (:require
    [org.httpkit.client :as http]
    [wines.util :refer [format-query-params]]
    [wines.constants :refer [conf protocol api-path]]))

(defn api-call [api-resource query-params response-handler api-error-handler]
  (http/get
     (str protocol "://" api-path api-resource
          "?apikey=" (:api_key conf)
          (format-query-params query-params)) ;can pass options to GET
     (fn [{:keys [status headers body error]}] ;; asynchronous response handling
       (if error
         (api-error-handler error)
         (response-handler status headers body)))))
