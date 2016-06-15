(ns wines.gui
  (:require [seesaw.dev :as dev]
            [seesaw.core :as ss]))

(defn call-ui []
  (println "Calling ui soon!")
  nil)

(def bg-color "#fdfdfd")

(ss/native!)

(def window (ss/frame :title "Wine Search",
;;            :on-close :exit,    ; add this when done...?
;;                       :minimum-size [800 :by 400]
;;                       :resizable? false
              ))

(defn display [content]
  (ss/config! window :content content)   ; config! used to configuer frame, lbls etc
  content)

(def wine-lbl (ss/label "WINES"))
(def title-lbl (ss/label "Catalog Search"))
(def query-lbl (ss/label "Query:"))

(def search-input
  (ss/text
   :text "Cabernet Sauvignon"
   :editable? true
;;    :preferred-size  [100 :by 30]
;;    :maximum-size  [100 :by 20]
;;    :minimum-size  [640 :by 480]
   :columns 12))

(dev/show-options window)

(def search-button
  (ss/button
   :text "Find my wine!"
   :listen [:action (fn [event](ss/alert (ss/text search-input)))
            :mouse-entered #(ss/config! % :foreground :blue)
            :mouse-exited #(ss/config! % :foreground :red)]))

(defn show-window []
  ;;   (invoke-later
  (-> window ss/pack! ss/show!) ;
  ;;       (.setLocationRelativeTo nil) ; add when done development
;;   (-> window .toFront)
  )
;;)

(defn label-with-input [label input]
  ;;   (ss/config!
  (ss/horizontal-panel
   :items [label input]
   :background bg-color
   :border nil))

(def container
  (ss/flow-panel
   :items [
           wine-lbl
           title-lbl
           (label-with-input query-lbl search-input)
           search-button]
   :background bg-color))


;; (show-window)

;; (display container)


;; (ss/listen window
;;   :component-resized (fn [event] (println event)))
;; (display search-button)
;; (ss/dispose! window) ; closes window.. destroying it..

;; (.toFront f)
;; (.repaint f)
;; (invoke-later
;;     (-> (frame :title "Hello",
;;            :content "Hello, Seesaw",
;;            :on-close :exit)
;;      pack!
;;      show!))
