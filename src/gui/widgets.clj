(ns gui.widgets
  (:require [seesaw.core :as ss]
            [gui.constants :refer :all]))

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


(def title-lbl (ss/label "Catalog Search"))

(def wine-com-img
  (ss/label
   :icon
    (clojure.java.io/resource "images/winecom_logo.png")
;;     "http://cache.wine.com/images/logos/80x80_winecom_logo.png"
    ))

(defn label-with-input [label input]
  (ss/horizontal-panel
   :items [label input]
   :background bg-color
   :border nil
   :class :label-input))
