(use 'fw.matching)

(def shielded-character-map
  {:location [1 8]
   :equipment {:armor {:ac 8
                       :max-dex 0}
               :main-hand {:damage (range 1 9)
                           :damage-type :slashing}
               :off-hand {:ac 2}}})

(def unshielded-character-map
  {:location [2 10]
   :equipment {:armor {:ac 4
                       :max-dex 2}
               :main-hand {:damage (range 1 5)
                           :damage-type :piercing}
               :off-hand {:damage (range 1 5)
                          :damage-type :piercing}}})

;; These two functions are equivilent in pretty much every way. Except perhaps readability.
(defn total-ac-with-matching [character]
  (matching [{:equipment {:armor {:ac armor-ac}
                          :off-hand {:ac shield-ac}}} character]
            (if shield-ac
              (+ armor-ac shield-ac)
              armor-ac)))

(defn total-ac-with-let [character]
  (let [{{{armor-ac :ac} :armor
          {shield-ac :ac} :off-hand} :equipment} character]
    (if shield-ac
      (+ armor-ac shield-ac)
      armor-ac)))
