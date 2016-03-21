(defproject learn-go "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [devcards "0.2.1-6"]
                 [reagent "0.6.0-alpha"]
                 [secretary "1.2.3"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-1"]
            [lein-externs "0.1.6"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]

  :source-paths ["src" "test"]

  :cljsbuild {:builds [{:id "devcards"
                        :source-paths ["src" "test"]
                        :figwheel {:devcards true}
                        :compiler {:main       "learngo.cards"
                                   :asset-path "js/compiled/devcards_out"
                                   :output-to  "resources/public/js/compiled/learngo_cards.js"
                                   :output-dir "resources/public/js/compiled/devcards_out"
                                   :source-map-timestamp true }}
                       {:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {:main       "learngo.main"
                                   :asset-path "js/compiled/out"
                                   :output-to  "resources/public/js/compiled/learngo.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :source-map-timestamp true }}
                       {:id "hostedcards"
                        :source-paths ["src" "test"]
                        :compiler {:main "learngo.cards"
                                   :devcards true
                                   :externs ["externs/app.js"]
                                   :asset-path "js/compiled/out"
                                   :output-to  "resources/public/js/compiled/learngo_cards.js"
                                   :optimizations :advanced}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:main       "learngo.main"
                                   :externs ["externs/app.js"]
                                   :asset-path "js/compiled/out"
                                   :output-to  "resources/public/js/compiled/learngo.js"
                                   :optimizations :advanced}}]}
  :figwheel { :css-dirs ["resources/public/css"] })
