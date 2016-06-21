(ns wines.constants
  (:require
    [wines.util :refer [handle-program-error parse-json]]))

(def api-path "services.wine.com/api/beta2/service.svc/json/")

(def protocol "http")

(def cli-options
  [["-s" "--search QUERY" "Query string from cli"]
   ["-h" "--help" "Prints help. GUI will start if no cli args passed."]])

; read json config file
(def conf
  (parse-json
    (try
      (slurp (clojure.java.io/resource "conf.json"))
      (catch java.io.FileNotFoundException e
        (handle-program-error "File does not exist.")))
    "Invalid json in conf.json"))
