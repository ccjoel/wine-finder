(ns gui.util
  (:require [seesaw.core :as ss]
            [gui.constants :refer :all]
            [wines.util :refer [parse-json]]))

(ss/native!)

(defn display [frame content]
  (ss/config! frame :content content)   ; config! used to configuer frame, lbls etc
  content)

(defn find-wine-object [wine-blurb wines-payload]
  (filter
    (fn [item]
      (= (str (:Id item)) (re-find #"\d+" wine-blurb)))
    wines-payload))

(defn get-rows [wine-object-list]
  (into [] (first wine-object-list)))

(defn label-with-input [label input]
  (ss/horizontal-panel
   :items [label input]
   :background bg-color
   :border nil
   :class :label-input))


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

(def results-model
  (for [product mocked-body]
    (let [simpler-product (select-keys product [:Name :Id])]
      (clojure.string/join
        " "
        [(format "%-8d" (:Id simpler-product))
         (:Name simpler-product)]
        ))))


;; (def obj (find-wine-object "145612   Rombauer Chardonnay 2014" mocked-body))

;; (filter

;; (def res
;; ;; (filter (fn [i] (= "java.lang.String" (type (last i))))
;;         (map
;;   (fn [item]
;;     [(name (first item)) (last item)]
;;     )
;;   (get-rows obj))
;; ;;         )
;;   )


