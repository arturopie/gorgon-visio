(ns gorgon-visio.ping-handler
  (:require [langohr.channel   :as lch]
            [langohr.basic     :as lb]
            [clojure.data.json :as json])
  (:use [gorgon-visio.connection :only (conn)]))

(def ping_response
  (json/write-str {:type "ping_response"
                   :hostname (.getCanonicalHostName (java.net.InetAddress/getLocalHost))
                   :version "gorgon-visio"
                   :worker_slots 0}))

(defn ping-handler [{msg-type "type" reply_exchange_name "reply_exchange_name" body "body"}]
  (let [ch   (lch/open conn)]
    (lb/publish ch reply_exchange_name "" ping_response :content-type "application/json")))
