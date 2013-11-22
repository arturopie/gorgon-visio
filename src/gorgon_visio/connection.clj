(ns gorgon-visio.connection
  (:require [langohr.core      :as rmq]
            [clojure.data.json :as json])
  (:use [gorgon-visio.helpers :only (keys-to-keywords)]))

(def config-file "config.json")

(def conn-info (keys-to-keywords
                ((json/read-str (slurp config-file)) "connection")))

(def conn (rmq/connect conn-info))
