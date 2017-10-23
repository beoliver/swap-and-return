# swap-and-return

A very simple library (well, it's just a function) for updating atoms and returning values

## Usage

```clojure
(ns thread-safe-queues.core
  (:require [swap-and-return.core :refer :all]))

(defn queue [& elems]
  (atom (into [] elems)))

(defn push!!
  [queue val]
  (swap! queue conj val) true)

;; for the pop operation we want to return the popped value to the calling thread

(def pop!!
  (partial swap-and-return!
           empty? ; test if the underlying vector is empty
           (fn [xs] {:atom (subvec xs 1) ; the new value for the atom
                     :user (first xs)} ; the value to return
		     )))

(def q (apply queue [1 2 3 4]))

> (pop!! q)
{:value 1}
> (pop!! q)
{:value 2}
> (push!! q "hello")
true
> (pop!! q)
{:value 3}
> (pop!! q)
{:value 4}
> (pop!! q)
{:value "hello"}
> (pop!! q)
{:empty true}
> (pop!! q)
{:empty true}
> (push!! q nil)
true
> (pop!! q)
{:value nil}
> (pop!! q)
{:empty true}
>
```
