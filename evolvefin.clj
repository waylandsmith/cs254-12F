(ns evolvefin
  (:require [clojure.zip :as zip]))


;;; in progress


(defn hp-major [year]
  "returns the party in the majority of the House at a given year"
;;  (get-in
  );)
  
  
  
(defn two-branch [year party]
  "does a party control the White House and the House of Reps?"
  )

;;;

(def house-seats {
                  1980 {:dem 243 :gop 192}
                  1982 {:dem 269 :gop 166}
                  1984 {:dem 253 :gop 182}
                  1986 {:dem 258 :gop 177}
                  1988 {:dem 260 :gop 175}
                  1990 {:dem 267 :gop 167}
                  1992 {:dem 258 :gop 176}
                  1994 {:dem 230 :gop 204}
                  1996 {:dem 206 :gop 228}
                  1998 {:dem 211 :gop 223}
                  2000 {:dem 212 :gop 221}
                  2002 {:dem 204 :gop 229}
                  2004 {:dem 202 :gop 232}
                  2006 {:dem 233 :gop 202}
                  2008 {:dem 257 :gop 178}
                  2010 {:dem 193 :gop 242}
                  2012 {:dem 201 :gop 234}
            }
  )

  
(def presidencies {
                   1980 :gop
                   1984 :gop
                   1988 :gop
                   1992 :dem
                   1996 :dem
                   2000 :gop
                   2004 :gop
                   2008 :dem
                   2012 :dem                   
                   }
  )


; do evolution, even if it's pathetic evolution!

(def gov-mansions {
                   1980 {:dem 31 :gop 19}
                   1990 {:dem 28 :gop 20}
                   2000 {:dem 19 :gop 29}
                   2010 {:dem 20 :gop 29}
                   }
  )

(def state-low {
                2004 {:dem 00 :gop 00}
                2006 {:dem 00 :gop 00}
                2008 {:dem 00 :gop 00}
                2010 {:dem 17 :gop 31}
                2012 {:dem 19 :gop 24}
                }
  )

(def state-hi {
               1980 {:dem 00 :gop 00}
               1990 {:dem 00 :gop 00}
               2000 {:dem 00 :gop 00}
               2010 {:dem 00 :gop 00}
               }
  )



; write a function set

(defn party-num [party]
  ; turns dem and gop into numbers
  (cond (identical? party :dem) -1.0
        (identical? party :gop) 1.0
        )
  )

(defn num-party [num]
  ; turns numbers into dem and gop
  (cond (( == -1.0 num) :dem)
        (( == 1.0 num) :gop))
  )

(defn pparty [year]
  ; which party won the Presidency?
  (party-num (get presidencies year)))


(defn gober-p [year party]
  ; takes a #### and a keyword
  (get-in gov-mansions [year party])
  )

(defn gober [year party]
  ; takes a #### and a float, returns whether or not 
  (cond ( == party -1.0 (gober-p year :dem))
        ( == party 1.0 (gober-p year :gop))        
         )
  )

;;;; in progress

(defn has-major [body dem gop]
  "who has the majority of a body in a year?"
  (cond ( > dem (/ body 2.0)) -1.0
        ( > gop (/ body 2.0)) 1.0
        :else 0
        )
  )


(def target-prezzy
  [[1980 1.0]
   [1984 1.0]
   [1988 1.0]
   [1992 -1.0]
   [1996 -1.0]
   [2000 1.0]
   [2004 1.0]
   [2008 -1.0]
   [2012 -1.0]]
  )


;;; create a function that describes who held the presidency in the past
; that'll be your final operation
;;;

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

;;;;;Matt's comments:  that's just what I will do



(def target-data
  ; Left:  Partisan control of the House of Representatives
  ; Right: Partisan control of the Presidency
  [[-1.0 1.0]
   [-1.0 1.0]
   [-1.0 1.0]
   [-1.0 -1.0]
   [1.0 -1.0]
   [1.0 1.0]
   [1.0 1.0]
   [-1.0 -1.0]
   [1.0 -1.0]
   ])


;; An individual will be an expression made of functions +, -, *, and
;; pd (protected division), along with terminals x and randomly chosen
;; constants between -5.0 and 5.0. Note that for this problem the 
;; presence of the constants actually makes it much harder, but that
;; may not be the case for other problems.

;; We'll the functions and the arities in a map.

(def function-table (zipmap '(+ - * pd)
                            '(2 2 2 2 )))

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

;; Exercises:
;; - Remove the numerical constants and see how this affects problem-solving
;;   performance.
;; - Add the "inc" function (arity 1) and see how this affects problem-solving
;;   performance.
;; - Run this on a different data set of your own choosing.
;; - Replace various hard-coded parameters with variables or arguments to 
;;   allow for easier experimentation with different parameter sets.
;; - Add additional functions of various arities to the function set and see
;;   how this affects problem-solving performance.
