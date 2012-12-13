(ns other)
; ABOUT

; This file contains commented versions of my larger explorations of Clojure this semester.  I've commented it as best I can.

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; for show and tell Thursday 9/27
;;;;; got this from a wikibook link below
;;;;; link: http://en.wikibooks.org/wiki/Clojure_Programming/Examples/Temperature_Converter_GUI
(import '(javax.swing JFrame JLabel JTextField JButton) 
        '(java.awt.event ActionListener) 
        '(java.awt GridLayout))
        
(let [wbox (new JFrame "Graphical Test")
      msg (new JLabel "Hello World")]
  (doto wbox
    ;(.setDefaultCloseOperation (JFrame/EXIT_ON_CLOSE)) ; this line is from the wikibook
    (.add msg)
    (.setSize 100 50) ; adapted with different sizes
    (.setVisible true))) ; from the wikibook

(let [copycat (new JFrame "Mirror, Mirror...")
      ; a program that copies what you say
      in-box (new JTextField)
      out-box (new JLabel)
      copy-button (new JButton "Magic Mirror")] ; need to find a way to get this to take in-box
  (. copy-button
     (addActionListener ; I guess - looking at the wikibook - this tells Java to look for input
                        (proxy [ActionListener] [] ; not sure what any of this means - also taken from the Wikibook
                          (actionPerformed [evt] ; wikibook - in this case, it seems to be looking for an event
                                           (let [blah (str (. in-box (get-text)))]
                                             (. out-box
                                                (setText (blah)))))))) ; modified from the wikibook - this is frustrating
  (doto copycat
    ;(.setDefaultCloseOperation (JFrame/EXIT_ON_CLOSE)) ; this line is from the wikibook
    (.setLayout (new GridLayout 2 2 3 3)) ; taken straight from the wikibook - maybe it makes things not overlap?
    (.add in-box)
    (.add out-box)
    (.add copy-button)
    (.setSize 200 150) ; adapted with different sizes
    (.setVisible true))) ; from the wikibook

 ; we've currently got an awesome app that lets you input text, press a button, and display a bunch of Java error messages!

; these functions are all mine ^^
(defn secs-year[days]
  (* (* 1M (* 24 60 60)
     days)))
        
(defn pie-chart[diameter]
  ; problem set 1.2 - calculates an area for a given diameter cirlce
  ( * Math/PI (Math/pow (/ diameter 2) 2)))

(defn phys-force [mass, ac]
  ; F = ma
  (* mass ac))   

(defn phys-energy [mass]
  ; you know the one
  (* mass (Math/pow 299792458 2)))

(defn alchemy [mass, effic]
  ; if you could turn matter into energy....
  (* (phys-energy mass) effic))

; maybe later we can try and have fun with our physics functions
        
; try and make a function that will find combinations of numbers that add up to a total without using the same numbers
        

;;;;;;;;;;;;;;;;;;;;;;; homework for Show and Tell
;;;;;;;;;;;;;;;;;;;;;;; Thurs 4 October 2012

(defn die[sides]
  ; an arbitrary die rolling function from earlier work
  (inc (rand-int sides)))

(def health {:Commoner 4 :Warrior 8 :Expert 6})

(defn healthMaker [career, phys]
  ; create a health value
  (health :career))

; try to evolve a character dieroller?

(def accept-range {:min 3 :max 18})
(def max-dice 4)

(defn die-lots [sides, pool]
  ; rolls a number of identical die
  (repeatedly pool #(die sides)))
  

;;;;;;;;;;;;;;;;;;;;;;;;; Show and Tell 3?

(defn die[sides]
  ; an arbitrary die rolling function
  (inc (rand-int sides)))
        

(defn d10s[pool]
  (take pool (die 10)))

(defn d10[p]
  ; iterates a pool of 10-sided die
  (repeatedly p #(inc (rand-int 10))))

; too bad I can't do this imperatively - it's easy to incorporate 'reroll if result is <x>' features imperatively.
; compare this to my python die pooler
; def roller(dicePool):
;        while dicePool > 0:
;               rolled = random.randint(1,10)
;                if rolled == 10:
;                        dicePool += 1
;                print rolled
;                dicePool -= 1
; this is very much imperative but it makes it easy to add a feature like 'roll again on 10s'
; note that returning more than the pool in the case of 10 is a feature.


(defn d10-improved[p]
  ; iterates a pool of 10-sided die
  (repeatedly p #(inc (rand-int 10)
                      if 10 (inc p))))


