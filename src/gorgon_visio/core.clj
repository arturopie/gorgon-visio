(ns gorgon-visio.core
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.exchange  :as le]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]
            [clojure.data.json :as json]
            [gorgon-visio.ping-handler :as ph])
  (:use [gorgon-visio.connection :only (conn)]))

(def msg-handlers
  {"ping" ph/ping-handler})

(defn handle-msg [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  "Hanldes message"
  [(try
     (let [message (json/read-str (String. payload "UTF-8"))
           {msg-type "type" reply_exchange_name "reply_exchange_name" body "body"} message]
       ((msg-handlers msg-type) msg-type reply_exchange_name body))
     (catch Exception e (println (str "Exception catched: " (.getMessage e)))))])


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
