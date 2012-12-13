(ns rig)


(defn aligner []
  ; picks an alignment!
  (let [aln (inc (rand-int 9))
        al (cond (== aln 1) (prn "Chaotic Good")
                 (== aln 2) (prn "Neutral Good")
                 (== aln 3) (prn "Lawful Good")
                 (== aln 4) (prn "Chaotic Neutral")
                 (== aln 5) (prn "True Neutral")
                 (== aln 6) (prn "Lawful Neutral")
                 (== aln 7) (prn "Chaotic Evil")
                 (== aln 8) (prn "Neutral Evil")
                 (== aln 9) (prn "Lawful Evil")
                 )
        ]
    )
  )


(def classy {:adept {:hp 6 :bab 0.5 :fort 0 :ref 0 :will 2}
             :aristocrat {:hp 8 :bab 0.75 :fort 2 :ref 0 :will 0}
             :commoner {:hp 4 :bab 0.5 :fort 0 :ref 0 :will 0}
             :cleric {:hp 8 :bab 0.75 :fort 2 :ref 0 :will 2}
             :bard {:hp 6 :bab 0.75 :fort 0 :ref 2 :will 2}
             :barbarian {:hp 12 :bab 1 :fort 2 :ref 0 :will 0}
             :druid {:hp 8 :bab 0.75 :fort 2 :ref 0 :will 2}
             :monk {:hp 8 :bab 0.75 :fort 2 :ref 2 :will 2}
             :fighter {:hp 10 :bab 1 :fort 2 :ref 0 :will 0}
             :paladin {:hp 10 :bab 1 :fort 2 :ref 0 :will 0}
             :ranger {:hp 8 :bab 0.5 :fort 2 :ref 2 :will 0}
             :rogue {:hp 6 :bab 0.75 :fort 0 :ref 2 :will 0}
             :sorcerer {:hp 4 :bab 0.5 :fort 0 :ref 0 :will 2}
             :wizard {:hp 4 :bab 0.5 :fort 0 :ref 0 :will 2}
             :psion {:hp 4 :bab 0.5 :fort 0 :ref 0 :will 2}
             :psywar {:hp 8 :bab 0.75 :fort 2 :ref 0 :will 0}
             :wilder {:hp 6 :bab 0.75 :fort 0 :ref 0 :will 2}
             :soulknife {:hp 10 :bab 0.75 :fort 2 :ref 0 :will 0}
             })


(defn die[sides]
 ; an arbitrary die rolling function
 (inc (rand-int sides)))

(defn d18 []
 ; makes d20 stats
 (+ (die 6)(die 4)(die 4) (die 4))
 )

(defn ab-bonus [score]
 ; calculates the bonus given by a high ability score
 (cond (> score 11) (with-precision 1 :rounding HALF_DOWN (/ (- score 10M) 2))
       (== score 11) 0
       (== score 10) 0
       (== score 9) -1
       (< score 9) (* (with-precision 1 :rounding HALF_UP (/ (- 10M score) 2)) -1)
       ))

(defn get-ab [stat, data]
  ; a custom ab-bonus
  (ab-bonus (get data stat))
  )

(defn fetcher [tbl, s1, s2]
  ; a shortcut for using get-in
  (get-in tbl [s1 s2])
  )

(defn hip [con, data, tbl, cls, hp]
  ; calculates health
  (+ (get data con) (die (fetcher tbl cls hp)))
  )

(defn attack [bab, stat, lvl]
  ; calculates attack values
 (int (+ (* lvl bab) stat))
  )

(defn save [career, save, ab-list, abil]
  ; calculates save bonus
  (+ (get-in classy [career save]) (get ab-list abil))
  )

;; OO in Clojure
;;


(defn rig [cls]
 ; a d20 random individual generator
  (let [abils {:stre (d18) :dex (d18) :con (d18)
               :inte (d18) :wis (d18) :cha (d18)}
        abonus {:st (get-ab :stre abils)
                :de (get-ab :dex abils)
                :co (get-ab :con abils)
                :in (get-ab :inte abils)
                :wi (get-ab :wis abils)
                :ch (get-ab :cha abils)}     
        hp (cond ( == (hip :co abonus classy cls :hp) 0) (inc (hip :co abonus classy cls :hp)) ; why isn't this working?
                 ( == (hip :co abonus classy cls :hp) 0M) (inc (hip :co abonus classy cls :hp))
                 :else (hip :co abonus classy cls :hp))
        svs {:fortitude (save cls :fort abonus :co)
             :reflex (save cls :ref abonus :de)
             :will (save cls :will abonus :wi)}
        ]    
    (aligner)
    (println "Level 1 Human" cls)
    (println "Ability Scores" abils) ; these won't matter until I get the damn function returning a value somewhere
    (println "Ability Modifiers" abonus)
    (println "HP = " hp)
    (println "Saving throws: " svs)
    (println "Melee + " (attack (fetcher classy cls :bab) (get abonus :st) 1))
    (println "Ranged + " (attack (fetcher classy cls :bab) (get abonus :de) 1))
        )
 )



; Lee is lecturing on culture and trivial geography
; culture - according to Lee - is shared memory
; the meme pool moves more quickly but is less stable than the gene pool
