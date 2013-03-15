(ns fw.matching
  "Note: This implementation is not meant for production. It is an intellectual exercise to explore how `let` works"
  (:refer-clojure :exclude [destructure]))

;; TODO: The couple plays I use let should be using matching instead.
;; This means that matching needs to be fully defined before its helpers.

(declare destructure-single-binding)

(defn- destructure-vector-element
  "Given the symbol for the vector value, the binding, and the index of the binding, return a vector of simple bindings"
  [value-sym binding-form index]
  (destructure-single-binding binding-form `(nth ~value-sym ~index)))

(defn- destructure-vector
  "Destructures a vector"
  [vector-binding value-expr]
  (let [value-sym (gensym "matching_vector_")
        num-bindings (count vector-binding)
        indicies (range 0 num-bindings)
        bindings (map (partial destructure-vector-element value-sym) vector-binding indicies)]
    (reduce concat [value-sym value-expr] bindings)))

(defn- destructure-map-element
  "Given the binding map, the symbol for the map value, and the key into the map, return a vector of simple bindings"
  [map-binding value-sym key]
  (let [binding-form (get map-binding key)
        value-expr `(get ~value-sym ~key)]
    (destructure-single-binding binding-form value-expr)))

(defn- destructure-map
  "Destructures a map"
  [map-binding value-expr]
  (let [value-sym (gensym "matching_map_")
        map-keys (keys map-binding)
        binding-from-map-key (partial destructure-map-element map-binding value-sym)
        bindings (map binding-from-map-key map-keys)]
    (reduce concat [value-sym value-expr] bindings)))

(defn- destructure-single-binding
  "Takes a single destructuring binding and returns a vector of simple bindings"
  [binding-form value-expr]
  (cond
   (symbol? binding-form) [binding-form value-expr]
   (vector? binding-form) (destructure-vector binding-form value-expr)
   (map? binding-form) (destructure-map binding-form value-expr)
   true (throw (new Exception (str "Unsupported binding form: " binding-form)))))

(defn- destructure
  "Takes a vector of name-sym value-expr pairs and returns a vector of simple bindings"
  [bindings]
  (when (not (vector? bindings)) (throw (new IllegalArgumentException (str "Bindings must be a vector."))))
  (when (odd? (count bindings)) (throw (new IllegalArgumentException (str "Must have an even number of bindings."))))
  (vec (apply concat (let [binding-pairs (partition 2 bindings)]
                       (map (partial apply destructure-single-binding)
                            binding-pairs)))))

(defmacro matching
  "Like let, but maps destructure as literals, instead of in reverse:

  (matching [{:x x} {:x 1}]
            x) ;; returns 1

As a result of the different map destructuring, advanced features of let may not be supported.
"
  [bindings & body]
  `(let ~(destructure bindings)
     ~@body))
