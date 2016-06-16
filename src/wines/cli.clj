(ns wines.cli
  (:require
   [gui.core :refer [call-ui]]
   [wines.util :refer [handle-program-error]]
   [wines.http :refer [api-call]]))

;; ---------- TODO: remove this section
; hardcoding resource to catalog for now
(def api-resource "catalog")

; need to set as default api param if search is on (basically if not using gui...)

;; ----------- END TODO

(defn print-cli-headers [help]
  (println "Welcome Wine Lovers.")
  (when help
    (println "Options available:")))

(defn parse-cli-args [cli-args]
  (if (:errors cli-args)
    (do
      (println (:summary cli-args))
      (println (:errors cli-args))
      (handle-program-error "Wrong arguments provided."))
    (let [opts (:options cli-args)]
      (cond
       (empty? opts) (call-ui)
       ; todo, change "not nil" to just checking if val exists?
       (not (nil? (:help opts))) (do
                                   (print-cli-headers true)
                                   (println (:summary cli-args)))
       (not (nil? (:search opts))) (api-call api-resource opts)))))
