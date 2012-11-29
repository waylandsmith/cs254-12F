(ns evolve1)

;;; got all of these from the following tutorial:https://gnuvince.wordpress.com/2008/10/31/fetching-web-comics-with-clojure-part-1/
(import '(java.net URL)
        '(java.lang StringBuilder)
        '(java.io BufferedReader InputStreamReader))

;;; borrowed code ends

; decide on races to compare

; gather data
; what is it I want to do?
; I want to make a list of variables to track
; unemployment?  change in food prices since last election?
; inflation?  change in GDP?
; polls?

; what about predicting 3rd party vote share?

; food prices?
; murder rate / crime rate?
; electricity prices
; mean temperature
; FEMA spending on relief
; is wartime?
; who controlled redistricting last cycle?


; college class size
; mean/median high school class size

;
; # of Governors for <party>
; # of Senators for <party>
; # of Representatives for <party>
; Incumbent Party controls congress?
;


; just get data first,
; then figure out what exactly to do

; 

; election data - all hail Stevus!


;;; borrowed code from https://gnuvince.wordpress.com/2008/10/31/fetching-web-comics-with-clojure-part-1/
(defn fetch-num
  "returns the URL as a string"
  [address]
  (let [url (URL. address)]
    (with-open stream (. url (openStream))
               (let [buf (BufferedReader. (InputStreamReader. stream))]
                 (apply str (line-seq buf))))))
  

;;; borrowed code ends

(def *elect* 
  [
   ])

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
  (cond ( == -1.0 num) :dem
        ( == 1.0 num) :gop)
  )

(defn pparty [year]
  ; which party won the Presidency?
  (party-num (get presidencies year)))


(defn gober-p [year party]
  ; takes a #### and a keyword
  (get-in gov-mansions [year party])
  )

(defn gober [year party]
  ; takes a #### and a float
  (cond ( == party -1.0 (gober-p year :dem))
        ( == party 1.0 (gober-p year :gop))        
         )
  )



; after function set:  find a fitness case (problem to solve)
;; eg:  who wins presidencies in a given year?
; for thurs:  write functions to do IO that give output as floating point #s
;;; such as partisan legislative control
;;; fitness cases: pairs of year, 1.0 for DFL win 0 for GOP win
;;; see if I can evolve a predictor function
;; can add functions as long as they take floats are args and outs

;; ideal way for the future:  instead of looking at years, look at years back from now
;;; relative data is useful



