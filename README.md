# swap-and-return

A very simple library (well, it's just a function) for updating atoms and returning values

[![Clojars Project](https://img.shields.io/clojars/v/beoliver/swap-and-return.svg)](https://clojars.org/beoliver/swap-and-return)

## Usage

```clojure
(ns thread-safe-queues.core
  (:require [swap-and-return.core :as s]))

(defn queue [& elems]
  (atom (into [] elems)))

(defn push!!
  [queue val]
  (swap! queue conj val) true)

;; for the pop operation we want to return the popped value to the calling thread

(def pop!!
  (partial s/swap-and-return!
           empty?
           (fn [xs] {::s/atom (subvec xs 1)
                     :val (first xs)
                     :some-key "some other data"})))

(def q (apply queue [1 2 3 4]))

> (pop!! q)
{:val 1, :some-key "some other data"}
> (pop!! q)
{:val 2, :some-key "some other data"}
> (pop!! q)
{:val 3, :some-key "some other data"}
> (pop!! q)
{:val 4, :some-key "some other data"}
> (pop!! q)
nil
> (push!! q 5)
true
> (pop!! q)
{:val 5, :some-key "some other data"}
> (pop!! q)
nil

```
