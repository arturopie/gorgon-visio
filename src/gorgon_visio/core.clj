(ns gorgon-visio.core
  (:gen-class)
  (:require [langohr.core      :as rmq]
            [langohr.channel   :as lch]
            [langohr.queue     :as lq]
            [langohr.exchange  :as le]
            [langohr.consumers :as lc]
            [langohr.basic     :as lb]))

(defn start-consumer
  "Starts a consumer bound to the given topic exchange in a separate thread"
  [ch topic-name]
  (let [queue-name (format "gorgon.visio")
        handler    (fn [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
                     (println (format "[consumer] received %s"  (String. payload "UTF-8"))))]
    (lq/declare ch queue-name :exclusive false :auto-delete true)
    (lq/bind    ch queue-name topic-name)
    (lc/blocking-subscribe ch queue-name handler :auto-ack true)))

(defn -main
  [& args]
  (let [conn  (rmq/connect)
        ch    (lch/open conn)
        ex    "gorgon.jobs"]
    (le/declare ch ex "fanout")
    (start-consumer ch ex)
    (rmq/close ch)
    (rmq/close conn)))
