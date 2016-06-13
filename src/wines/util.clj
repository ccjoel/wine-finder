(ns wines.util)

(defn with-abs-path [filename]
  (str (.getCanonicalPath (clojure.java.io/file ".")) (java.io.File/separator) filename))
