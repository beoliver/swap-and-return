(ns swap-and-return.core
  "A very basic library (one function) to help with atomic updates
  that return values (eg popping an element from a stack).
  To avoid complications the value to return must happen during the `swap!`."
  (:refer-clojure :exclude [atom]))

(def ^:const atom ::atom)

(defn swap-and-return!
  "returns a user defined map or `nil`

  The predicate `is-empty?` is used to test if the content of the atom is empty.
  The function `update-fn` is called when `is-empty?` returns `false`.
  If `is-empty?` returns true then `nil` will be returned.

  `update-fn` is given access to the current state of the atom and MUST return a map with
  at least the key `swap-and-return/atom`, the val of which is the new state of the atom.

  On a successful swap, (i.e non empty content) the user map modulo
  the `::atom` key and value will be retuned to the user.

  As `update-fn` is called within `swap!` is should have no side effects as it may be called multiple times.
  The third arg `the-atom` is the atom to call the function on (most probably used as a partial function)"
  [is-empty? update-fn the-atom]
  (let [m (clojure.core/atom nil)]
    (swap! the-atom (fn [a]
                      (if (is-empty? a)
                        (do (reset! m nil) a)
                        (let [data (update-fn a)]
                          (do
                            (reset! m (dissoc data ::atom))
                            (::atom data))))))
    (deref m)))
