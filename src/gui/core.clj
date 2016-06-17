(ns gui.core
  (:require
    [clojure.pprint :refer [pprint]]
    [seesaw.dev :as dev]
    [seesaw.core :as ss]
    [wines.http :refer [api-call]]
    [gui.constants :refer :all]
    [gui.util :refer :all]
    [gui.widgets :refer :all]))

(ss/native!)

(defn display [frame content]
  (ss/config! frame :content content)   ; config! used to configuer frame, lbls etc
  content)

;; todo: prompt user for api key?

(def window (ss/frame :title "Wine Finder",
                      :menubar main-menu
                      ;;            :on-close :exit,    ; add this on prod env
                      ;;                       :resizable? false  ; todo: prod env
                      :size [950 :by app-height]
                      ))


(def search-input
  (ss/text ; could replace with ss/input later
    :text "Cabernet Sauvignon"
    :editable? true
    :columns 14))

; todo: this is not working currently. checkpoint...
(defn search-button-handler [query]
  ; todo: api-call blocks... so show a loading.. or something..
  ; todo: deref api promise then act
  (let [ finished (api-call
    "catalog" {:search query}
    (fn [status headers body]
      (when (= status 200)
        (ss/alert "success!")
        (pprint body)))
    #(ss/alert (str "Error " %))) ]
  ))

(def search-button
  (ss/button
    :text "Find my wine!"
    :listen [:action (fn [event](search-button-handler (ss/text search-input)))
             :mouse-entered #(ss/config! % :foreground :red)
             :mouse-exited #(ss/config! % :foreground :black)]))

(defn show-window []
  (ss/invoke-later
    (-> window ss/show! (.setLocationRelativeTo nil)) ; add when done development
    (-> window .toFront)))

(def wine-properties-table
  (ss/scrollable
    (ss/table
      :id :wine-properties
      :show-grid? true
      :show-horizontal-lines? true
      :show-vertical-lines? true
      :fills-viewport-height? true
      ;;       :auto-resize :all-columns
      :model [:columns ["Property" "Value"] :rows []])
    :id :wine-properties-scroller
    :background bg-color
    :size wine-properties-size
    :border [1 "Wine Properties" 10]))

(def wine-results
  (ss/scrollable
    (ss/listbox
      :model results-model
      :id :results
      :background :white
      :foreground :black
      :border nil
      ; todo: change color when selecting items form box,
      ;;                          using renderer/ :listen :selection
      :listen [:selection
               (fn [event]
                 (let [selected (ss/selection event)
                       selected-model (find-wine-object selected mocked-body)]
                   (ss/config! (ss/select (ss/to-root event) [:#wine-properties])
                               :model [:columns ["Name" "Value"]
                                       :rows (get-rows selected-model)])))])
    :size wine-results-size
    :border [2 "Results" 10]
    ))


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
                        :items [search-input search-button])]
              :background bg-color)
            (ss/horizontal-panel
              :items [ wine-results wine-properties-table ]
              :background  bg-color)
            ]))

(defn call-ui []
  (display window container)
  (show-window)
  true)

;; (call-ui)
