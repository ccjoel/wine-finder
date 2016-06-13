(ns wines.core
  (:require
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.tools.logging :as log]
    [org.httpkit.client :as http]

    [wines.constants :refer [api-path protocol]]
    [wines.util :refer [with-abs-path handle-program-error parse-json]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


;;;;;;;;;;;;;;;;;;;; CLI CODE


(def cli-mode? true)

(def cli-options
  [["-s" "--search QUERY" "query string from cli"]
   ["-h" "--help" "Prints help."]])

; parse cli options



;; TODO







; read json config file
(def conf
  (parse-json
   (try
     (slurp (with-abs-path "resources/conf.json"))
     (catch java.io.FileNotFoundException e
       (handle-program-error "File does not exist.")))
   "Invalid json in conf.json"))


; make http api call

(def api-resource "catalog")

(def query-params "")

(defn is-200? [status]
  (= status 200))

(defn handle-api-response [status headers body]
  (when (is-200? status)
    (let [json-res (parse-json body "Api response was not valid json.")]
      (:Status json-res))))

(defn api-call [api-resource]
  (http/get (str protocol "://" api-path api-resource "?apikey=" (:api_key conf)) ;options
            (fn [{:keys [status headers body error]}] ;; asynchronous response handling
              (if error
                (println "Failed, exception is " error)
                (do
                  (println "Async HTTP GET: " status)
                  (println "headers: " headers)
                  (handle-api-response status headers body))))))

