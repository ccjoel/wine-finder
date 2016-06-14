(ns wines.util
  (:require
   [clojure.string :as s]
   [clojure.data.json :as json]))

(defn with-abs-path [filename]
  (str (.getCanonicalPath (clojure.java.io/file ".")) (java.io.File/separator) filename))

(defn exit-now! []
   (System/exit 0))

; TODO: receive param if runnning from GUI for a different behavior
(defn handle-program-error [msg]
  (println "Error in program:")
  (println msg)
  (exit-now!))

(defn parse-json [json-string error-msg]
  (try
    (json/read-str json-string :key-fn keyword)
    (catch java.lang.Exception e
      (handle-program-error error-msg))))

(defn format-query-params [query-params]
  (s/join
   (for [param query-params]
     (str "&" (s/join "=" [(name (key param)) (val param)])))))
