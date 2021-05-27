(ns deepgreen-camel-clj.core
  (:gen-class)
  (:require [clj-camel.core :refer :all]
            [clj-camel.util :as cu]
            ;; [org.apache.camel.builder.endpoint.StaticEndpointBuilders :reference :all]
            [dotenv :refer [env app-env]])

  (:import (org.apache.camel.component.exec ExecBinding)
           (org.apache.commons.net.ftp FTPClientConfig)))

(def route1 (route-builder
             (from (env 'INPUT_SOURCE))
             (to "file://target/")
             (set-header ExecBinding/EXEC_COMMAND_ARGS (simple "${in.header.CamelFileNameProduced}"))
             (log "Stored locally: ${in.header.CamelFileNameProduced}")
             (to "exec://file-importer.py")))

(def ^:dynamic main (org.apache.camel.main.Main.))

(defn -main
  "Setting up Main object, CamelContext and starting routes."
  [& args]
  (println "Starting main routes")
  (.start main)
  (def ^:dynamic ctx (.getCamelContext main))
  (add-routes ctx route-1)

  (.run main)

