;; (merge-with + wine-properties-size wine-results-size) ; only if inputs are maps

;; (ss/config! wine-properties-table
;; ;;             :size [400 :by 400]
;;             :bounds [:* :* 300 300])

;; (ss/listen window
;;   :component-resized (fn [event] (println event)))

;; (ss/dispose! window) ; closes window.. destroying it..

;; (def app-img (ss/label
;;               :text "App Img"
;;               :size [150 :by 150]
;;               :icon (clojure.java.io/resource "images/wine-bottles.png")))


;; (defn render-property [item {:keys [value]}]
;;   ; todo clean this keys / value implementation
;;   (ss/config! item :background :white :foreground :black :border nil))

;; (def wine-properties-list
;;     (ss/scrollable
;;       (ss/listbox
;;         :id :wine-properties
;;         :model ["Description: Hello World" "Location: left right"]
;;         :border [1 14]
;;         ;;       :size [(- 400 20) :by (- max-height 30)]
;;         :background :white
;;         :renderer render-property)
;;       :size wine-properties-size
;;       :border nil))
