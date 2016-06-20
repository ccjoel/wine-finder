(ns wines.core
  (:require
   [clojure.tools.cli :refer [parse-opts]]
   ;;     [clojure.tools.logging :as log]
   ;;    [wines.util :refer [handle-program-error]]
   [wines.cli :refer [parse-cli-args]]
   [wines.constants :refer [cli-options]])
  (:gen-class))

(defn -main
  "Decide what to do... make api call or show ui."
  [& args]
    (parse-cli-args (parse-opts args cli-options)))
