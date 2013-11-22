(ns gorgon-visio.job-definition-handler)

(defn job-handler [{msg-type "type" reply_exchange_name "reply_exchange_name" :as message}]
  (println (str "Received job definition: " message " and exch name " reply_exchange_name)))
