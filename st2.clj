(ns st2)

; for show and tell Thursday 9/20

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


;; Problem Set 1: 
;; - Write an expression that calculates the number of seconds in a year.
;; - Define diameter to be some number and write expression that calculates the area
;;   of a circle with that diameter.
;; - Write arithmetic expressions for other formulas that you remember from high school
;;   (or look some up if you don't remember any!).
        
; 1
(defn days-year[days]
  (* (* 1M (* 24 60 60)
     days)))
        
(defn die[sides]
  ; an arbitrary die rolling function
  (inc (rand-int sides)))
        
        
        
        
        
        
        
        
        
        
        
        
        