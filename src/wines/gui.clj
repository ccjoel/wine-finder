(ns wines.gui
  (:require [seesaw.dev :as dev]
            [seesaw.core :as ss]
            [wines.util :refer [parse-json]]))

(defn call-ui []
  (println "Calling ui soon!")
  nil)

(def bg-color "#fdfdfd")

(def max-height 530)
(def app-height 600)

(ss/native!)

(def window (ss/frame :title "Wine Finder",
;;            :on-close :exit,    ; add this when done...?
;;                       :resizable? false  ; todo: resizable false
                      :size [950 :by app-height]
              ))

(defn display [content]
  (ss/config! window :content content)   ; config! used to configuer frame, lbls etc
  content)

(def tool-bar (ss/toolbar :items ["File" "About"]))

(def title-lbl (ss/label "Catalog Search"))

;; (def app-img (ss/label
;;               :text "App Img"
;;               :size [150 :by 150]
;;               :icon (clojure.java.io/resource "images/wine-bottles.png")))

(def wine-com-img
  (ss/label
   :icon "http://cache.wine.com/images/logos/80x80_winecom_logo.png"))

(def search-input
  (ss/text ; could replace with ss/input
   :text "Cabernet Sauvignon"
   :editable? true
   :columns 14))

(def search-button
  (ss/button
   :text "Find my wine!"
   :listen [:action (fn [event](ss/alert (ss/text search-input)))
            :mouse-entered #(ss/config! % :foreground :red)
            :mouse-exited #(ss/config! % :foreground :black)]))

(defn show-window []
  ;;   (invoke-later
  (-> window ss/show!)
  ;;       (.setLocationRelativeTo nil) ; add when done development
;;   (-> window .toFront)
  )
;;)

(defn label-with-input [label input]
  (ss/horizontal-panel
   :items [label input]
   :background bg-color
   :border nil
   :class :label-input))


(def mocked-body
  (reduce
    into
    (replicate
      5
      (:List
        (:Products
          (parse-json (slurp (clojure.java.io/resource "body.seed.json")) "w.e"))))))



;; (prn (:Name (first mocked-body)))

(def results-model (for [product mocked-body]
  [(:Name (select-keys product [:Name]))]))



(def results (ss/scrollable (ss/listbox :model results-model
                         :id :results
                         :border [2 "Results" 10]
                         :background :white
                         :foreground :black
                         :size [(- 300 20) :by (- max-height 30)]
                         ; todo: change color when selecting items form box,
;;                          using renderer/ :listen :selection
                         :listen [:selection
                                  (fn [event]
                                    (println "You selected " (ss/selection event)))]
                         )
                            :size [300 :by max-height]
                            :border nil
                            ))


(defn render-property [renderer {:keys [value]}]
  ; todo clean this keys / value implementation
  (ss/config! renderer :background :white :foreground :black :border nil))

(def result-properties
  (ss/scrollable (ss/listbox
                   :id :result-properties
                   :model ["Description: Hello World" "Location: left right"]
                   :border [1 14]
                   :size [(- 400 20) :by (- max-height 30)]
                   :background :white
                   :renderer render-property)

                 :size [400 :by max-height]
                 :border nil
                 )
  )


(def container
  (ss/flow-panel
    :vgap   20
    :background bg-color
    :align  :center
    :items [(ss/flow-panel
                :align :center :vgap 10 :size [210 :by max-height]
                :items [(ss/flow-panel
                          :size [98 :by 110]
                          :items [wine-com-img title-lbl]
                          :background bg-color)
                        (ss/grid-panel
                          :rows 2 :vgap 3
                          :background bg-color
                          :items [search-input search-button] )]
                :background bg-color)
            (ss/horizontal-panel :items [ results result-properties ])]))

(show-window)
;;
(display container)



;; (ss/listen window
;;   :component-resized (fn [event] (println event)))

;; (ss/dispose! window) ; closes window.. destroying it..
