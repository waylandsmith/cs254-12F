(ns st2
;  (require [clojure.java.io :as io]) ; got this from http://www.beaconhill.com/blog/?p=283
  )

; for show and tell Thursday 9/27

(import '(javax.swing JFrame JLabel JTextField JButton) ; got this from a wikibook
        '(java.awt.event ActionListener) ; link: http://en.wikibooks.org/wiki/Clojure_Programming/Examples/Temperature_Converter_GUI
        '(java.awt GridLayout)
        
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
                 (. out-box
                    (setText (in-box))))))) ; modified from the wikibook - this is frustrating
  (doto copycat
    ;(.setDefaultCloseOperation (JFrame/EXIT_ON_CLOSE)) ; this line is from the wikibook
    (.setLayout (new GridLayout 2 2 3 3)) ; taken straight from the wikibook - maybe it makes things not overlap?
    (.add in-box)
    (.add out-box)
    (.add copy-button)
    (.setSize 200 150) ; adapted with different sizes
    (.setVisible true))) ; from the wikibook

 ; we've currently got an awesome app that lets you input text, press a button, and display a bunch of Java error messages!

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
        
        
        