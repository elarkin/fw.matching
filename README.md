# fw.matching


I've always found the way clojure.core/let does map destructuring to be a little confusing.

I'm still learning Clojure, so I understand that there may be some hidden reason for it
working the way it does.

When I first heard that Clojure had map destructuring, I expected it to work by putting
a map literal on the left hand side. Instead, you have to reverse the key and value positions.

This library provides a naive implementation of what I originally thought map destructuring meant.

A Clojure library with an alternate destructuring ideology to the one in clojure.core/let

matching destructures maps as if they are literals:

    (matching [some-map-value {:x 1}
               {:x x} some-map-value]
      x) ;; evaluates to 1 and is equivilent to the below let:
    (let [some-map-value {:x 1}
          {x :x} some-map-value]
      x)
