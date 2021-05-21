(ns deepgreen-camel-clj.core
  (:gen-class)
  (:require [clj-camel.core :refer :all]
            [clj-camel.util :as cu]
            ;; [org.apache.camel.builder.endpoint.StaticEndpointBuilders :reference :all]
            [dotenv :refer [env app-env]])

  (:import (org.apache.camel.component.exec ExecBinding)
           (org.apache.commons.net.ftp FTPClientConfig)))

(defn ftp
  ""
  [^String host ^String username ^String password]
  (str "ftp://" host "?passiveMode=true&move=.done&username=" username "&password=" password))

(def route1 (route-builder
             (from (ftp (env 'FTP_HOST) (env 'FTP_USER) (env 'FTP_PASS)))
             (to "file://target/")
             (set-header ExecBinding/EXEC_COMMAND_ARGS (simple "${in.header.CamelFileNameProduced}"))
             (log "Stored locally: ${in.header.CamelFileNameProduced}")
             (to "exec://file-importer.py")))

(def ^:dynamic ctx (camel-context))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Starting main routes")
   (add-routes ctx route1)
   (.start ctx)

    ;; (Thread/sleep 5000)
    ;; (.shutdown ctx)
   )
