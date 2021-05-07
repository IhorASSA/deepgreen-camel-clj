(ns deepgreen-camel-clj.core
  (:gen-class)
  (:require [clj-camel.core :refer :all]
            [clj-camel.util :as cu]
            ;; [org.apache.camel.builder.endpoint.StaticEndpointBuilders :reference :all]
            )
  (:import (org.apache.camel.component.exec ExecBinding)
           (org.apache.commons.net.ftp FTPClientConfig)))

(defn ftp
  ""
  [^String host ^String username ^String password]
  (str "ftp://" host "?passiveMode=true&move=.done&username=" username "&password=" password))

(def route1 (route-builder
             (from (ftp "194.44.29.184" "ftp_exchange" "rSac04jEr2ZuLXp4"))
             (to "file://target/")
             (set-header ExecBinding/EXEC_COMMAND_ARGS (simple "${in.header.CamelFileNameProduced}"))
             (log "Stored locally: ${in.header.CamelFileNameProduced}")
             (to "exec://file-importer.py")))

(def route2 (route-builder
             (from "file://target/?move=.handled")
             (log "executed ${in.body}")
             (split (json-path "$.features") {:agg-strategy        grouped-exchange-strategy
                                              :streaming           true
                                              :parallel-processing true}
                    (to "seda://splitted_input"))))

(def route3 (route-builder
             (from "seda://splitted_input")
             (log "${body}")))
             ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
             ;; (from "timer://heartbeat?period=60") ;;
             ;; (set-body (constant "test"))
             ;; ;; (throttle 2 {:async-delayed false
             ;; ;; :reject-execution false ;;
             ;;    :time-period-millis     5000})
             ;; ;; (log "${body} after throttling") ;;
             ;; (to "seda:result"))) ;;
             ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(def ^:dynamic ctx (camel-context))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ((println "Starting main routes")
   (add-routes ctx route2 route3)
   (.start ctx)

    ;; (Thread/sleep 5000)
    ;; (.shutdown ctx)
   ))
