# fw.matching

A Clojure library with an alternate destructuring ideology to the one in clojure.core/let

matching destructures maps as if they are literals:

    (matching [some-map-value {:x 1}
               {:x x} some-map-value]
      x) ;; evaluates to 1 and is equivilent to the below let:
    (let [some-map-value {:x 1}
          {x :x} some-map-value]
      x)
