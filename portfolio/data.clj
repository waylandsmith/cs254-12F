(ns data)

; Matthew Meneghini
; ABOUT
; this file holds my data for my Genetic Programming final
; as well as a number of operations on this data
; The comments explain what they do.

(def house-seats {
                  ; number of representatives in the US House of Reprs
                  1980 {:dem 243 :gop 192}
                  1982 {:dem 269 :gop 166}
                  1984 {:dem 253 :gop 182}
                  1986 {:dem 258 :gop 177}
                  1988 {:dem 260 :gop 175}
                  1990 {:dem 267 :gop 167}
                  1992 {:dem 258 :gop 176}
                  1994 {:dem 204 :gop 230}
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

(defn hs-converter [yr]
  "converts house-seats to a vector"
  (vec (get house-seats yr))
  ) ; I'd hoped to use this function to painlessly reformat the house-seats data
; but no such luck

(defn house-vec [ntimes]
  (let [counter ntimes
        yr 1980]
    (defn convert []
      (hs-converter yr)
      (inc(inc(yr)))
      (inc counter)     
      )
    (cond (== ntimes 0) nil
          :else (convert))
    )
  ) ; this whole thing was programmed imperatively.  Also badly.  It was an attempt
; to iterate hs-converter on the entire house-seats stack

(def hs ; used in final-1
  ; d, r #
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
   [201.0 234.0]]
  )
  
(def presidencies { ; fairly self-explanatory
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

;;;; these next sections were about redistricting - hence only the '00' years
;;;; but I had very little data and couldn't shoehorn it in
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

;;;;


; function sets - I'd originally hoped to write my own frontend to the GP
; system using functions like these to format my data into floats

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

;;;; in-progress functions

(defn has-major [body dem gop]
  "who has the majority of a body in a year?"
  (cond ( > dem (/ body 2.0)) -1.0
        ( > gop (/ body 2.0)) 1.0
        :else 0
        )
  )


;;;;;


(def all-dat ; used for final-2.  Numbers originally expressed as ratios.
  "house of representatives (D/R), Presidency"
  [[243.0/192 1.0]
   [269.0/166 1.0]
   [253.0/182 1.0]
   [258.0/177 1.0]
   [260.0/175 1.0]
   [267.0/167 1.0]
   [258.0/176 -1.0]
   [204.0/230 -1.0]
   [206.0/228 -1.0]
   [211.0/223 -1.0]
   [212.0/221 1.0]
   [204.0/229 1.0]
   [202.0/232 1.0]
   [233.0/202 1.0]
   [257.0/178 -1.0]
   [193.0/242 -1.0]
   [201.0/234.0 -1.0]]
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
  ) ; my first GP data and attempt.  Didn't work as well as the new stuff!
