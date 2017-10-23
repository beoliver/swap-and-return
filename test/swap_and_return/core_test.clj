(ns swap-and-return.core-test
  (:require [clojure.test :refer :all]
            [swap-and-return.core :refer :all]))

(defn queue [& elems]
  (atom (into [] elems)))

(defn push!!
  [queue val]
  (swap! queue conj val) true)

(def pop!!
  (partial swap-and-return!
           empty?
           (fn [xs] {:atom (subvec xs 1)
                     :user (first xs)})))

(def q (apply queue [1 2 3 4]))
