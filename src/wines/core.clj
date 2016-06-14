(ns wines.core
  (:require
    [clojure.tools.cli :refer [parse-opts]]
    [clojure.tools.logging :as log]
    [clojure.string :as s]
    [org.httpkit.client :as http]

    [wines.constants :refer [api-path protocol]]
    [wines.util :refer [with-abs-path handle-program-error parse-json]])
  (:gen-class))



;;;;;;;;;;;;;;;;;;;; CLI CODE


(def cli-mode? (atom true))

(def cli-options
  [["-s" "--search QUERY" "Query string from cli"]
   ["-h" "--help" "Prints help. GUI will start if no cli args passed."]])

; parse cli options

(defn call-ui []
  (println "Calling ui soon!")
  nil)

; todo: handle params outside of main, so can continue working without that part

; hardcoding resource to catalog for now
(def api-resource "catalog")

; read json config file
(def conf
  (parse-json
   (try
     (slurp (with-abs-path "resources/conf.json"))
     (catch java.io.FileNotFoundException e
       (handle-program-error "File does not exist.")))
   "Invalid json in conf.json"))



; make http api call



(defn is-200? [status]
  (= status 200))

(defn handle-api-response [status headers body]
  (when (is-200? status)
    (let [json-res (parse-json body "Api response was not valid json.")]
      (:Status json-res))))

(defn format-query-params [query-params]
  (s/join
   (for [param query-params]
     (str "&" (s/join "=" [(name (key param)) (val param)])))))

(defn api-call [api-resource query-params]
  (http/get
   (str protocol "://" api-path api-resource "?apikey=" (:api_key conf)
        (format-query-params query-params)) ;options
   (fn [{:keys [status headers body error]}] ;; asynchronous response handling
     (if error
       (println "Failed, exception is " error)
       (do
         (println "Async HTTP GET: " status)
         (println "headers: " headers)
         (handle-api-response status headers body))))))


(defn parse-cli-args [cli-args]
  (prn cli-args)
  (if (:errors cli-args)
    (do (println (:summary cli-args))
      (println (:errors cli-args))
      (handle-program-error "Wrong arguments provided."))
    (let [opts (:options cli-args)]
      (println (str opts))
      (cond
       (empty? opts) (call-ui)
       (not (nil? (:help opts))) (println (:summary cli-args))
;;        (not (nil? (:search opts))) (api-call api-resource opts)
       ;(not (nil? (:)))
        ; else: parse params and run the app! (or show help)
       )

      )))


(defn -main
  "Decide what to do... make api call or show ui."
  [& args]
  (let [cli-args (parse-opts args cli-options)]
    (parse-cli-args cli-args)))





