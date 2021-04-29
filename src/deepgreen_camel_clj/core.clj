(ns deepgreen-camel-clj.core
  (:gen-class)
  (:require [clj-camel.core :refer :all]))

(def route1 (route-builder
             (from "ftp://ftp_exchange@194.44.29.184?password=rSac04jEr2ZuLXp4&passiveMode=true&move=.done")
             (log "log ${in.header.CamelFileName}")
             (to "file://target/")))

(def route2 (route-builder
             (from "timer://foo?fixedRate=true&period=60")
             (set-body (constant "test"))
             (throttle 2 {:async-delayed      false
                          :reject-execution   false
                          :time-period-millis 5000})
             (log "${body} after throttling")
             (to "seda:result")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [ctx (camel-context)]
    (println "I don't do a whole lot ... yet.")
    (add-routes ctx route1)
    (.start ctx)
    (Thread/sleep 10000)
    (.shutdown ctx)))
