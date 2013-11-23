(ns gorgon-visio.msg-handlers
  (:require [clojure.data.json :as json]
            [gorgon-visio.ping-handler :as ph]
            [gorgon-visio.job-definition-handler :as jdh])
  (:use [gorgon-visio.helpers :only (exception-guard)]))

(def msg-handlers
  {"ping" ph/ping-handler
   "job_definition" jdh/job-handler})

(defn default-handler [msg-type reply_exchange_name body]
  (println (str "Received unrecognized message type: " msg-type)))

(defn handle-msg [ch {:keys [content-type delivery-tag type] :as meta} ^bytes payload]
  "Hanldes message"
  (exception-guard (fn [] (let [message (json/read-str (String. payload "UTF-8"))
                                {msg-type "type"} message]
                            ((msg-handlers msg-type default-handler) message)))))
