(ns swap-and-return.core-test
  (:require [clojure.test :refer :all]
            [swap-and-return.core :as s]))

(defn queue [& elems]
  (atom (into [] elems)))

(defn push!!
  [queue val]
  (swap! queue conj val) true)

(def pop!!
  (partial s/swap-and-return!
           empty?
           (fn [xs] {::s/atom (subvec xs 1)
                     :val (first xs)
                     :some-key "some other data"})))

(def q (apply queue [1 2 3 4]))
