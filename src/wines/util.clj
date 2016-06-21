(ns wines.util
  (:require
   [clojure.string :as s]
   [clojure.data.json :as json])
  (:import [java.net URLEncoder]))

;; (defn with-abs-path [filename]
  ; TODO: maybe change "." with *file*
;;   (str (.getCanonicalPath (clojure.java.io/file ".")) (java.io.File/separator) filename)
;;   (clojure.java.io/resource filename))

(defn exit-now! []
   (System/exit 0))

; TODO: receive param if runnning from GUI for a different behavior
(defn handle-program-error [msg]
  (println "Error in program:")
  (println msg)
  (exit-now!))

(defn parse-json
  ([json-string]
   (parse-json json-string "No error message provided."))
  ([json-string error-msg]
   (try
     (json/read-str json-string :key-fn keyword)
     (catch java.lang.Exception e
       (handle-program-error error-msg)))))

(defn format-query-params [query-params]
  (s/join
   (for [param query-params]
     (str "&" (s/join "=" [(name (key param)) (URLEncoder/encode (str (val param)) "UTF-8")])))))
