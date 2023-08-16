(ns passwd-gen.passgen)

(defn generate-password [length]
  (let [used-chars (reduce (fn [acc val]
                             (str acc (char val))) "" (range 33 123))]
    (loop [password ""]
      (if (= (count password) length)
        password
        (recur (str password (rand-nth used-chars)))))))
