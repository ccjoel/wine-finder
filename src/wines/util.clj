(ns wines.util
  (:require
   [clojure.data.json :as json]))


(defn with-abs-path [filename]
  (str (.getCanonicalPath (clojure.java.io/file ".")) (java.io.File/separator) filename))


(defn exit-now! []
   (System/exit 0))


(defn handle-program-error [msg]
  (println msg)
  (exit-now!))


(defn parse-json [json-string error-msg]
  (try
    (json/read-str json-string
                   :key-fn keyword)
    (catch java.lang.Exception e
      (handle-program-error error-msg))))


