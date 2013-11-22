(ns gorgon-visio.core
  (:gen-class)
  (:require [teporingo.broker :as broker]
            [clj-etl-utils.log :as log])
  (:use
   [teporingo.core   :only [*reply-code* *reply-text* *exchange* *routing-key* *message-properties* *listener* *conn* *props* *body*
                            *active* *confirm-type* *delivery-tag* *multiple* publisher publish]]
   [clj-etl-utils.lang-utils :only [raise]])
  )

(defn handle-returned-message []
  (log/errorf
   "[publisher] RETURNED: conn=%s code=%s text=%s exchange=%s routing-key:%s props=%s body=%s"
   @*conn*
   *reply-code*
   *reply-text*
   *exchange*
   *routing-key*
   *props*
   (String. *body*)))

(broker/register
 :amqp01
 {:name               :amqp01
  :roles              #{:local}
  ;; :user              "guest"
  ;; :pass              "guest"
  ;; :host              "localhost"
  :port               25671
  :connection-timeout 10
  :reconnect-delay-ms 1000
  ;; :heartbeat-seconds 1
  :vhost              "/"
  ;; :use-confirm        false
  ;; :basic-qos          {:prefetch-size 0 :prefetch-count 1}
  ;; :use-transactions   false
  ;; :auto-ack           false
  :listeners          {:return  handle-returned-message}})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Changed again!"))
