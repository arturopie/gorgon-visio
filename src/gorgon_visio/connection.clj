(ns gorgon-visio.connection
  (:require [langohr.core      :as rmq]))

(def conn (rmq/connect {:host "localhost"}))
