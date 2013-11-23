(ns gorgon-visio.helpers)

(defn keys-to-keywords [hash]
  (into {}
        (for [[k v] hash]
          [(keyword k) v])))

(defn exception-guard [f]
  [(try
     (f)
     (catch Exception e (println "Exception catched.") (.printStackTrace e)))])
