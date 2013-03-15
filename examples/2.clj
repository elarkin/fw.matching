(ns fw.examples.example-2
  (:refer-clojure :exclude [let])
  (:use [fw.matching :rename {matching let}]))

;; You could use matching /as/ let if you really wanted to.

(let [x 1]
  (println x))