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
             (from "file://target/*.geojson?move=.handled")
             (log "executed ${in.body}")
             (split (json-path "$.features") {:agg-strategy        grouped-exchange-strategy
                                              :streaming           true
                                              :parallel-processing true})
             (log "splitted ${in.body}")))


(def route4 (route-builder
             (from "pgevent:localhost:5432/deepgreen/new_spatial_data?user=deepgreen&pass=deepgreen2021MT!")
             (log "New event received: ${in.body}")))

(def ^:dynamic ctx (camel-context))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Starting main routes")
   (add-routes ctx route4)
   (.start ctx)

    ;; (Thread/sleep 5000)
    ;; (.shutdown ctx)
   )
