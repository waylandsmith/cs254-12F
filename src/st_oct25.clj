
; we are making a random level 1 D&D human individual

(defn die[sides]
  ; an arbitrary die rolling function
  (inc (rand-int sides)))
  
(defn d18 []
  ; makes d20 stats
  (+ (die 6)(die 4)(die 4) (die 4))
  ; how did I allow this function to name a map / piece of data for 'stat'?
  )

(def hp {:adept 6 :commoner 4 :cleric 8 :bard 6 :barbarian 12 
           :druid 8 :monk 8 :fighter 10 :paladin 10 :ranger 8
           :rogue 6 :sorcerer 4 :wizard 4 :psion 4 :psywar 8 
           :wilder 6 :soulknife 10})

(defn ab-bonus [score]
  ; calculates the bonus given by a high ability score
  (cond (> score 11) (with-precision 1 :rounding HALF_DOWN (/ (- score 10M) 2))
        (== score 11) 0
        (== score 10) 0
        (== score 9) -1
        (< score 9) (* (with-precision 1 :rounding HALF_UP (/ (- 10M score) 2)) -1)
        ))

(defn rig [cls]
  ; a d20 random individual generator
  (let [abils {:stre (d18) :dex (d18) :con (d18)
               :inte (d18) :wis (d18) :cha (d18)}]
    (println abils) ; these won't matter until I get the damn function returning a value somewhere
    (print "Health = " )
    (+ (ab-bonus (get abils :con)) (inc(rand-int(get hp cls))))
    )
  )


(def classy {:adept {:hp 6 :bab 1} 
             :commoner {:hp 4 :bab 1} 
             :cleric {:hp 8 :bab 2}
             :bard {:hp 6 :bab 2}
             :barbarian {:hp 12 :bab 3}
             :druid {:hp 8 :bab 2} 
             :monk {:hp 8 :bab 2}
             :fighter {:hp 10 :bab 3}
             :paladin {:hp 10 :bab 3}
             :ranger {:hp 8 :bab 1}
             :rogue {:hp 6 :bab 2}
             :sorcerer {:hp 4 :bab 1}
             :wizard {:hp 4 :bab 1}
             :psion {:hp 4 :bab 1}
             :psywar {:hp 8 :bab 2}
             :wilder {:hp 6 :bab 2}
             :soulknife {:hp 10 :bab 2}})