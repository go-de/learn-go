(ns learngo.utils)

(defn conjv [coll & elts]
  (if (vector? coll)
    (apply conj coll elts)
    (apply conj (vec coll) elts)))
