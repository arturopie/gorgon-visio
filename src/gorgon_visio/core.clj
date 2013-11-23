(ns gorgon-visio.core
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.exchange  :as le]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb])
  (:use [gorgon-visio.connection :only (conn)]
        [gorgon-visio.msg-handlers :only (handle-msg)]))

(defn start-listener
  "Starts a consumer bound to the given topic exchange in a separate thread"
  [ch topic-name]
  (let [queue-name (format "gorgon.visio")
        handler    handle-msg]
    (lq/declare ch queue-name :exclusive false :auto-delete true)
    (lq/bind    ch queue-name topic-name)
    (println "Listening...")
    (lc/blocking-subscribe ch queue-name handler :auto-ack true)))

(defn -main
  [& args]
  (let [ch    (lch/open conn)
        ex    "gorgon.jobs"]
    (le/declare ch ex "fanout")
    (start-listener ch ex)
    (rmq/close ch)
    (rmq/close conn)))
