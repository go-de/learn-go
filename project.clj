(defproject learn-go "0.1.0-SNAPSHOT"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [org.clojure/core.async "0.3.465"]
                 [philoskim/debux "0.4.1"]
                 [devcards "0.2.4"]
                 [reagent "0.7.0"]
                 [secretary "1.2.3"]
                 [reagent-forms "0.5.32"]
                 [com.taoensso/timbre "4.10.0"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.14"]
            [lein-externs "0.1.6"]]

  :aliases {"dev"     ["figwheel" "dev" "devcards"]
            "release" ["cljsbuild" "once" "release"]}

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
                                   :output-dir "resources/public/js/compiled/dev_out"
                                   :source-map-timestamp true }}
                       {:id "hostedcards"
                        :source-paths ["src" "test"]
                        :compiler {:main "learngo.cards"
                                   :devcards true
                                   :externs ["externs/app.js"]
                                   :asset-path "js/compiled/out"
                                   :output-to  "resources/public/js/compiled/learngo_cards.js"
                                   :output-dir "resources/public/js/compiled/hostedcards_out"
                                   :optimizations :advanced}}
                       {:id "prod"
                        :source-paths ["src"]
                        :compiler {:main       "learngo.main"
                                   :externs ["externs/app.js"]
                                   :asset-path "js/compiled/out"
                                   :output-to  "resources/public/js/compiled/learngo.js"
                                   :output-dir "resources/public/js/compiled/prod_out"
                                   :optimizations :advanced}}]}
  :figwheel { :css-dirs ["resources/public/css"] })
