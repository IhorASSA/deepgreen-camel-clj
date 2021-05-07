(ns deepgreen-camel-clj.core
  (:gen-class)
  (:require [clj-camel.core :refer :all]
            [clj-camel.util :as cu]
            ;; [org.apache.camel.builder.endpoint.StaticEndpointBuilders :reference :all]
            )
  (:import (org.apache.camel.component.exec ExecBinding)
           (org.apache.commons.net.ftp FTPClientConfig)))

;; (def ftp-client
;;   (bean))

(def route1 (route-builder
             (from "ftp://194.44.29.184?ftpClieasdfasdfntParameters=#ftp-client")
             (log "log ${in.header.CamelFileName}")
             (to "file://target/")
             (set-header ExecBinding/EXEC_COMMAND_ARGS (simple "${in.header.simple.CamelFileNameProduced}"))

             (to "exec://file-importer.py")))

(def route2 (route-builder
             (from "timer://foo?fixedRate=true&period=1000")
             (set-header ExecBinding/EXEC_COMMAND_ARGS (constant 'echo.py'))
             (to "exec://echo.py")
             (log "executed: ${body}")))
             ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
             ;; (from "timer://heartbeat?period=60")          ;;
             ;; (set-body (constant "test"))                  ;;
             ;; (throttle 2 {:async-delayed      false        ;;
             ;;              :reject-execution   false        ;;
             ;;              :time-period-millis 5000})       ;;
             ;; (log "${body} after throttling")              ;;
             ;; (to "seda:result")))                          ;;
             ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def ^:dynamic ctx)
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (
    (binding [ctx (camel-context )]
    (println "I don't do a whole lot ... yet.")
    (add-routes ctx route1)
    (.start ctx)

    ;; (Thread/sleep 5000)
    ;; (.shutdown ctx)
    )))
