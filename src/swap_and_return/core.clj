(ns swap-and-return.core
  "A very basic library (one function) to help with atomic updates
  that return values (eg popping an element from a stack).
  To avoid complications the value to return must happen during the `swap!`.")

(defn swap-and-return!
  "returns a map that will have exactly `one` of the following key value pairs.
  `{:empty true}` or `{:value <the user return value>}`

  The predicate `is-empty?` is used to test if the content of the atom is empty.
  The function `update-fn` will ONLY be called if `is-empty?` returns `false`.
  It is given access to the current state of the atom and MUST return a map with two keys `:user` `:atom`
  the val of `:user` is the value to be returned to the user and
  the val of `:atom` is the new state of the atom.
  As `update-fn` is called within `swap!` is should have no side effects as it may be called multiple times.
  The third arg `the-atom` is the atom to call the function on (most probably used as a partial function)"
  [is-empty? update-fn the-atom]
  (let [m (atom nil)]
    (swap! the-atom (fn [a]
                      (if (is-empty? a)
                        (do (reset! m {:empty true}) a)
                        (let [{:keys [user atom]} (update-fn a)]
                          (do
                            (reset! m {:value user})
                            atom)))))
    (deref m)))
