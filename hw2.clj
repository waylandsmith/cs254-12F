(ns hw2)

; this is for homework - show and tell

; try to figure out how to print a multiline string
; 9/12/12

;9/13/12

(use '[clojure.java.shell :only [sh]]) ; got this from internet, use to make java shell work?

(defn prompt[java.io.BufferedReader. *in*] ; does not work
  ; trying to create a simple shell to print strings
  (println ("Prompt-> "))
  )

(def blue (str "caerulea \n")) ; my first attempt to understand vars
(def notes 
  (str "This \n
        is \n
        a \n
        multiline \n
        string\n
        ")
  
  ) ; ugly, but - pardon me - functional.

(print notes)

(def note2
  (str "
        this
        is
        a
        multiline
        string \n"
       )
  ) ; works to take notes!

(print note2)

; die roller

(defn d6
  (+ 1 (rand-int 5))
  )

(defn d4[]
  (def roll (rand-int 4)) ; global variable, not local
  (def roll2 (+ 1 roll)) ; is supposed to turn the range from 0,3 to 1,4 and is not working
  print roll2
  )

; I/O again

(defn die[sides]
  ; an arbitrary die rolling function
  (inc (rand-int sides))) ; apparently inc is a good way to add +1


(defn gpDemo (str ("
                  > For thursday, have something for show and tell in Clojure,
                  > should be more than last week
                  > does not have to be GP
                  > Any Clojure code / question can go on the Moodle page
                  >> is recommended
                  > learn Clojure as fast as you can
                  >> must at least understand the Clojinc file
                  
                  Evolutionary Computation lecture
                  > goal is to get evolution to solve a problem on your computer
                  > Random Generation - Assessment - Selection - Variation - Reasessment - Repeat/Finish
                  > 'elitism' often doesn't work well - don't just take the best; take random samples and select among them
                  >> this slowly weights the population while preserving diversity
                  > Variation:  can be mutations or crossovers (asexual or sexual)
                  > can be better than brute force in certain applications:
                  'say you have a factory with 1000 switches, and you want to learn the most efficient combination of switches.
                  you model this, but trying all the combinations is 2 ^ 1000, which is just too much to model
                  so instead you genetically program, and successful combinations with inheritance, and weed out the 'weak'
                  you will have an option probably more creative than you could be; yet more feasible than brute-force.
                  '
                  
                  > GP is evolutionary computing to produce executable computer programs
                  > Programs are tested by executing them
                  > use GP when you know what you want, but now how to do it
                  > Lisp language syntactic simplicity makes it easy to write programs that evolve programs
                  > looks like the Polish notation helps with this selection and recombination
                  > symbolic regression - simply fits equations to data points, with fitness as an average error (closer to 0 the better)
                  > make sure no 'divide by zero' when evaluating - so use modulo instead of division?
                  > DO THAT Clojinc!
                  
                  > map takes a function and applies it to each input, instead of taking each input as an argument for one function
                  > can't actually use % as % in Clojure for some reason, make a 'protected division' function instead
                  "
                 ))
  gpDemo)


