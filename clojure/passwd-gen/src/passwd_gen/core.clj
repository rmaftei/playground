(ns passwd-gen.core
  (:require 
   [passwd-gen.passgen :refer [generate-password]]
   [clojure.data.json :as json])
  (:gen-class))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (generate-password 8))


(-main)