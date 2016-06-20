(ns gui.util
  (:require
    [wines.util :refer [parse-json]]  ;for mocking dev
    ))


(defn find-wine-object [wine-blurb wines-payload]
  (filter
    #(= (str (:Id %)) (re-find #"\d+" wine-blurb))
    wines-payload))


(defn format-key [item]
  [(name (key item)) (val item)])


(defn get-rows [wine-object-list]
  (into [] (map format-key (first wine-object-list))))


;;;; TODO: development, mocking. remove!
(def mocked-body
;;   (take 50
;;     (cycle
      (:List
        (:Products
          (parse-json
            (slurp (clojure.java.io/resource "body.seed.json")) "testing")))
;;       ))
  )

(defn parse-products-all [api-response]
  (:List
    (:Products
      (parse-json api-response))))

(defn parse-products [api-response]
  (let [ parsed-res (:List
                      (:Products
                        (parse-json api-response)))]
    (for [product parsed-res]
      (let [simpler-product (select-keys product [:Name :Id])]
        (clojure.string/join
          " "
          [(format "%-8d" (:Id simpler-product))  ; todo: format this correclty...
           (:Name simpler-product)]
          )))))

(def results-model
  (for [product mocked-body]
    (let [simpler-product (select-keys product [:Name :Id])]
      (clojure.string/join
        " "
        [(format "%-8d" (:Id simpler-product))
         (:Name simpler-product)]
        ))))
