(defproject deepgreen-camel-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [takeoff/clj-camel "1.6.4"]
                 [org.apache.camel/camel-ftp "3.8.0"]
                 [org.apache.camel/camel-log "3.8.0"]
                 ;; https://mvnrepository.com/artifact/org.apache.camel/camel-exec
                 [org.apache.camel/camel-exec "3.8.0"]
                 [log4j "1.2.17"]
                 [commons-logging "1.2"]
                 [org.slf4j/slf4j-api "2.0.0-alpha1"]
                 [org.slf4j/slf4j-simple "2.0.0-alpha1"]]
  :main ^:skip-aot deepgreen-camel-clj.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
