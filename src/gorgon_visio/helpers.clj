(ns gorgon-visio.helpers)

(defn keys-to-keywords [hash]
  (into {}
        (for [[k v] hash]
          [(keyword k) v])))
