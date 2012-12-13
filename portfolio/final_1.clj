(ns final-1
  (:require [clojure.zip :as zip]))

;;;;;;;;;;;;;;;;;ABOUT
; My final project - part 1
; Matthew Meneghini
; This uses Lee's 'evolvefn' gp system  to predict how many
; Republicans will be in the House of Representatives based on
; the number of Democrats for given Congressional election
; (1980 - 2012)

; most of this is meaningless - the two-party system makes this math easy
; but watching the computer come up with explanations for independents
; is somewhat amusing

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; LEE's GENETIC PROGRAMMING SYSTEM
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; This code defines and runs a genetic programming system on the problem
;; of finding a function that fits a particular set of [x y] pairs.

;; The aim here is mostly to demonstrate how genetic programming can be 
;; implemented in Clojure simply and clearly, and several things are 
;; done in somewhat inefficient and/or non-standard ways. But this should 
;; provide a reasonable starting point for developing more efficient/
;; standard/capable systems. 

;; Note also that this code, as written, will not always find a solution.
;; There are a variety of changes that one might make to improve its
;; problem-solving performance on the given problem. 

;; We'll use data from x^2 + x + 1 (the problem from chapter 4 of
;; http://www.gp-field-guide.org.uk/, although our gp algorithm won't
;; be the same, and we'll use some different parameters as well).

;; We'll use input (x) values ranging from -1.0 to 1.0 in increments
;; of 0.1, and we'll generate the target [x y] pairs algorithmically.
;; If you want to evolve a function to fit your own data then you could    
;; just paste a vector of pairs into the definition of target-data instead. 


(def target-data ; My Data - Matt
  ; left: Dems right: GOP
  [[243.0 192.0]
   [269.0 166.0]
   [253.0 182.0]
   [258.0 177.0]
   [260.0 175.0]
   [267.0 167.0]
   [258.0 176.0]
   [204.0 230.0]
   [206.0 228.0]
   [211.0 223.0]
   [212.0 221.0]
   [204.0 229.0]
   [202.0 232.0]
   [233.0 202.0]
   [257.0 178.0]
   [193.0 242.0]
   [201.0 234.0]])


(def function-table (zipmap '(+ - * pd fullhouse wave)
                            '(2 2 2 2 0 1)))

(defn random-function 
  []
  (rand-nth (keys function-table)))

(defn random-terminal
  []
  (rand-nth (list 'x (- (rand 10) 5))))

(defn random-code
  [depth]
  (if (or (zero? depth)
          (zero? (rand-int 2)))
    (random-terminal)
    (let [f (random-function)]
      (cons f (repeatedly (get function-table f)
                          #(random-code (dec depth)))))))

;; And we have to define pd (protected division):

(defn pd
  "Protected division; returns 0 if the denominator is zero."
  [num denom]
  (if (zero? denom)
    0
    (/ num denom)))

(defn fullhouse ; a custom 'operation' - in this case, just an easy way to account for the size of the House of Representatives 
  "how big is the House?"
  []
  435)

(defn wave [dems] ; none of the majorities is smaller than 9
  "returns -5 if the Democrats control the house"
  (cond (>= 0.5 (/ dems 435)) -5.0
        :else 1.0))
  

;; We can now evaluate the error of an individual by creating a function
;; built around the individual, calling it on all of the x values, and 
;; adding up all of the differences between the results and the 
;; corresponding y values.

(defn error 
  [individual]
  (let [value-function (eval (list 'fn '[x] individual))]
    (reduce + (map (fn [[x y]] 
                     (Math/abs 
                       (- (value-function x) y)))
                   target-data))))

;; We can now generate and evaluate random small programs, as with:

;; (let [i (random-code 3)] (println (error i) "from individual" i))

;; To help write mutation and crossover functions we'll write a utility
;; function that extracts something from an expression and another that
;; inserts something into an expression.

(defn codesize [c]
  (if (seq? c)
    (count (flatten c))
    1))

(defn at-index 
  "Returns a subtree of tree indexed by point-index in a depth first traversal."
  [tree point-index]
  (let [index (mod (Math/abs point-index) (codesize tree))
        zipper (zip/seq-zip tree)]
    (loop [z zipper i index]
      (if (zero? i)
        (zip/node z)
        (if (seq? (zip/node z)) 
          (recur (zip/next (zip/next z)) (dec i))
          (recur (zip/next z) (dec i)))))))

(defn insert-at-index
  "Returns a copy of tree with the subtree formerly indexed by
point-index (in a depth-first traversal) replaced by new-subtree."
  [tree point-index new-subtree]
  (let [index (mod (Math/abs point-index) (codesize tree))
        zipper (zip/seq-zip tree)]
    (loop [z zipper i index]
      (if (zero? i)
        (zip/root (zip/replace z new-subtree))
        (if (seq? (zip/node z))
          (recur (zip/next (zip/next z)) (dec i))
          (recur (zip/next z) (dec i)))))))

;; Now the mutate and crossover functions are easy to write:

(defn mutate
  [i]
  (insert-at-index i 
                   (rand-int (codesize i)) 
                   (random-code 2)))

(defn crossover
  [i j]
  (insert-at-index i 
                   (rand-int (codesize i)) 
                   (at-index j (rand-int (codesize j)))))

;; We can see some mutations with:
;; (let [i (random-code 2)] (println (mutate i) "from individual" i))

;; and crossovers with:
;; (let [i (random-code 2) j (random-code 2)]
;;   (println (crossover i j) "from" i "and" j))

;; We'll also want a way to sort a populaty by error that doesn't require 
;; lots of error re-computation:

(defn sort-by-error
  [population]
  (vec (map second
            (sort (fn [[err1 ind1] [err2 ind2]] (< err1 err2))
                  (map #(vector (error %) %) population)))))

;; Finally, we'll define a function to select an individual from a sorted 
;; population using tournaments of a given size.

(defn select
  [population tournament-size]
  (let [size (count population)]
    (nth population
         (apply min (repeatedly tournament-size #(rand-int size))))))

;; Now we can evolve a solution by starting with a random population and 
;; repeatedly sorting, checking for a solution, and producing a new 
;; population.

(defn evolve
  [popsize]
  (println "Starting evolution...")
  (loop [generation 0
         population (sort-by-error (repeatedly popsize #(random-code 2)))]
    (let [best (first population)
          best-error (error best)]
      (println "======================")
      (println "Generation:" generation)
      (println "Best error:" best-error)
      (println "Best program:" best)
      (println "     Median error:" (error (nth population 
                                                (int (/ popsize 2)))))
      (println "     Average program size:" 
               (float (/ (reduce + (map count (map flatten population)))
                         (count population))))
      (if (< best-error 0.1) ;; good enough to count as success
        (println "Success:" best)
        (recur 
          (inc generation)
          (sort-by-error      
            (concat
              (repeatedly (* 1/2 popsize) #(mutate (select population 7)))
              (repeatedly (* 1/4 popsize) #(crossover (select population 7)
                                                      (select population 7)))
              (repeatedly (* 1/4 popsize) #(select population 7)))))))))


;; Run it with a population of 1000:

(evolve 1000)

  
