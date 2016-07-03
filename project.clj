(defproject wines "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.json "0.2.6"]
                 [http-kit "2.1.18"]
                 [seesaw "1.4.5"]]
  :omit-source true
  :main ^:skip-aot wines.core
  :target-path "target/%s"
;  :jvm-opts ["-Xdock:name=Wine Finder"]
  :profiles {:uberjar {:aot :all}})
