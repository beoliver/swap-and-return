(defproject beoliver/swap-and-return "0.1.0"
  :description "A simple library for updating atoms and retrning values"
  :url "https://github.com/beoliver/swap-and-return"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :aot [swap-and-return.core]
  :deploy-repositories [["clojars" {:sign-releases false}]])
