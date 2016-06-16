(ns wines.gui
  (:require [seesaw.dev :as dev]
            [seesaw.core :as ss]
            [wines.util :refer [parse-json]]
            [clojure.pprint :refer [pprint]]))

(defn call-ui []
  (println "Calling ui soon!")
  nil)

(def bg-color "#fdfdfd")
(def max-height 530)
(def app-height 600)
(def wine-results-size [370 :by max-height])
(def wine-properties-size [400 :by (- max-height 30)])
(def main-search-size [210 :by max-height])

(ss/native!)


;;;; TODO: development, mocking. remove!
(def mocked-body
  (take 35
    (cycle
      (:List
        (:Products
          (parse-json
            (slurp (clojure.java.io/resource "body.seed.json")) "testing"))))))

(def results-model
  (for [product mocked-body]
    (let [simpler-product (select-keys product [:Name :Id])]
      (clojure.string/join
        " "
        [(format "%-8d" (:Id simpler-product))
         (:Name simpler-product)]
        ))))

;;;;;


;; todo: prompt user for api key?

; TODO: why menubar wont show?
(def main-menu
  (ss/menubar
    :items [(ss/menu
              :text "File"
              :items [(ss/action
                        :name "Open..."
                        :key "menu O"
                        :handler (fn [e] (println "Open something")))])
            (ss/menu
              :text "Edit"
              :items [(ss/action
                        :name "Undo"
                        :key "menu Z"
                        :handler (fn [e] (println "Undo something")))])
            (ss/menu
              :text "Help"
              :items [(ss/action
                        :name "About"
                        :key "menu H"
                        :handler (fn [e] (println "Show about dialog")))])]))

(def window (ss/frame :title "Wine Finder",
                      :menubar main-menu
;;            :on-close :exit,    ; add this when done...?
;;                       :resizable? false  ; todo: resizable false
                      :size [950 :by app-height]
              ))

(defn display [content]
  (ss/config! window :content content)   ; config! used to configuer frame, lbls etc
  content)

(def title-lbl (ss/label "Catalog Search"))

;; (def app-img (ss/label
;;               :text "App Img"
;;               :size [150 :by 150]
;;               :icon (clojure.java.io/resource "images/wine-bottles.png")))

(def wine-com-img
  (ss/label
   :icon "http://cache.wine.com/images/logos/80x80_winecom_logo.png"))

(def search-input
  (ss/text ; could replace with ss/input later
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


(def wine-results (ss/scrollable (ss/listbox :model results-model
                         :id :results
                         :background :white
                         :foreground :black
                         :border nil
                         ; todo: change color when selecting items form box,
;;                          using renderer/ :listen :selection
                         :listen [:selection
                                  (fn [event]
                                    (println "You selected " (ss/selection event)))]
;;                           :renderer (fn [item ev] (:Name item) )
                         )
                            :size wine-results-size
                            :border [2 "Results" 10]
                            ))


(defn render-property [item {:keys [value]}]
  ; todo clean this keys / value implementation
  (ss/config! item :background :white :foreground :black :border nil))

(def wine-properties-list
  (ss/scrollable
    (ss/listbox
      :id :wine-properties
      :model ["Description: Hello World" "Location: left right"]
      :border [1 14]
;;       :size [(- 400 20) :by (- max-height 30)]
      :background :white
      :renderer render-property)
    :size wine-properties-size
    :border nil))

(def wine-properties-table
  (ss/scrollable
    (ss/table
      :model [
               :columns ["prop-name" "value"]
               :rows [
                       ["desc" "it is"]
                       ["location" "PR"]
                       ]
               ]
;;       :border [1 14]
;;       :size [(- 400 20) :by (- max-height 30)]
      :background :white
;;       :renderer render-property
      )
    :size wine-properties-size
    :border nil))


(def container
  (ss/flow-panel
    :vgap   20
    :background bg-color
    :align  :center
    :items [(ss/flow-panel
                :align :center :vgap 10 :size main-search-size
                :items [(ss/flow-panel
                          :size [98 :by 110]
                          :items [wine-com-img title-lbl]
                          :background bg-color)
                        (ss/grid-panel
                          :rows 2 :vgap 3
                          :background bg-color
                          :items [search-input search-button] )]
                :background bg-color)
            (ss/horizontal-panel :items [ wine-results wine-properties-table ])]))

(show-window)

(display container)

;; (merge-with + wine-properties-size wine-results-size) ; only if inputs are maps

;; (ss/config! wine-properties :visible? true)

;; (ss/listen window
;;   :component-resized (fn [event] (println event)))

;; (ss/dispose! window) ; closes window.. destroying it..
