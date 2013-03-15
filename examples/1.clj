(use 'fw.matching)

;; I've always found the way clojure.core/let does map destructuring to be a little confusing.

;; I'm still learning Clojure, so I understand that there may be some hidden reason for it
;; working the way it does.

;; When I first heard that Clojure had map destructuring, I expected it to work by putting
;; a map literal on the left hand side. Instead, you have to reverse the key and value positions.

;; It gets even worse when you nest map inside map inside map. Perhaps I'm just a sadistic freak.

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
