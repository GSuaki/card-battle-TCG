(defproject card-battle "0.0.1"
  :description "Seller Invoices Postman"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/tools.logging "0.5.0"]
                 ; ioc and di
                 [com.stuartsierra/component "0.4.0"]
                 ; logging
                 [ch.qos.logback/logback-classic "1.1.8" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.22"]
                 [org.slf4j/jcl-over-slf4j "1.7.22"]
                 [org.slf4j/log4j-over-slf4j "1.7.22"]]
  :main ^{:skip-aot true} card-battle.application
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev     {:aliases               {"run-dev" ["trampoline" "run" "-m" "card-battle.application/run-dev"]}
                       :plugins               [[lein-cljfmt "0.5.7"]
                                               [lein-cloverage "1.1.1"]
                                               [test2junit "1.2.7"]
                                               [jonase/eastwood "0.3.5"]
                                               [lein-kibit "0.1.7"]]
                       :eastwood              {:exclude-linters [:no-ns-form-found]}
                       :test2junit-output-dir "test-results"
                       :test2junit-run-ant    false
                       :emma-xml?             true
                       :dependencies          [[org.clojure/clojure "1.10.1"]
                                               [midje "1.9.8"]
                                               [javax.servlet/javax.servlet-api "4.0.1"]
                                               [reloaded.repl "0.2.4"]
                                               [ring/ring-mock "0.4.0"]]}})
