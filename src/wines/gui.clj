(ns wines.gui
  (:require [seesaw.dev :as dev]
            [seesaw.core :as ss]))

(defn call-ui []
  (println "Calling ui soon!")
  nil)

(def bg-color "#fdfdfd")

(ss/native!)

(def window (ss/frame :title "Wine Finder",
;;            :on-close :exit,    ; add this when done...?
;;                       :minimum-size [800 :by 400]
;;                       :resizable? false
                      :size [900 :by 500]
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
  (ss/text
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
  (-> window ss/show!) ;  ss/pack!
  ;;       (.setLocationRelativeTo nil) ; add when done development
;;   (-> window .toFront)
  )
;;)

(defn label-with-input [label input]
  ;;   (ss/config!
  (ss/horizontal-panel
   :items [label input]
   :background bg-color
   :border nil
   :class :label-input))


(def results (ss/listbox :model ["Cabernet" "Blanc" "Merlot"]
                         :id :results
                         :border [2 "Results" 10]
                         :background :white
                         :foreground :black
;;                          :size [2000 :by 1000]
                         ))

(def result-properties
  (ss/listbox
    :id :result-properties
    :model ["Description: Hello World" "Location: left right"]
    :border [2 "Properties" 10]
    :minimum-size [600 :by 100]
    )
  )

(def container
  (ss/border-panel
    :west (ss/flow-panel
            :size [200 :by 300]
            :items [(ss/flow-panel
                      :size [98 :by 110]
                      :items [wine-com-img title-lbl]
                      :background bg-color
                      )
                    (ss/grid-panel
                      :rows 2
                      :vgap 3
                      :background bg-color
                      :items [search-input search-button]
                      )]
            :background bg-color)
    :center results
    :east result-properties
    ))

(show-window)

(display container)


;; (ss/toolbar )

;; (ss/listen window
;;   :component-resized (fn [event] (println event)))

;; (ss/dispose! window) ; closes window.. destroying it..

;; (.toFront f)
;; (.repaint f)
;; (invoke-later
;;     (-> (frame :title "Hello",
;;            :content "Hello, Seesaw",
;;            :on-close :exit)
;;      pack!
;;      show!))
