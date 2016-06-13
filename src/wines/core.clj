(ns wines.core
  (:require
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.tools.logging :as log]
    [clojure.data.json :as json]
    [org.httpkit.client :as http]

    [wines.constants :refer [api-path protocol]]
    [wines.util :refer [with-abs-path]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn handle-program-error [msg]
  (println msg)
  (System/exit 0))


; parse cli options

; read json config file

(def conf
  (try
    (json/read-str
      (slurp (with-abs-path "resources/conf.json"))
      :key-fn keyword)
  (catch java.lang.Exception e
      (handle-program-error "Invalid json"))
  (catch java.io.FileNotFoundException e
    (handle-program-error "File does not exist."))))


; make http api call

(def api-resource "catalog")

(def query-params "")

(defn api-call []
  (http/get (str protocol "://" api-path api-resource "?apikey=" (:api_key conf)) ;options
            (fn [{:keys [status headers body error]}] ;; asynchronous response handling
              (if error
                (println "Failed, exception is " error)
                (do
                  (println "Async HTTP GET: " status)
                  (println "headers: " headers)
                  (println "body" body)
                  )))))

; parse http response

