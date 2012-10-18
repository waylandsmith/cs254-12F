(ns cday)

; show and tell and stuff on Thurs 10/11/12

; couldn't you add an anti-inflation element to a fitness function?

; I am going to try to make a strategy / AI for one of the games I'm making in game design

; Fleamarket (Omri made it)
; as Lee for link
; if you simplify game, you can have GP play itself and that serves as fitness
; perhaps have a tourney / playoff system?
; perhaps it'd be better to do it for the TCG, not Bluetimber
; hmmm that's really interesting


; where do we go when we die?  Why, we go to the Great Recycle Bin in the sky!

; I KNOW KUNG F

(def values {:ace-l 1 :num "as face" :face 10 :ace-h 15})

(def suits {:hearts values :clubs values :diamonds values :spades diamonds})
; hmmm....


(def d6-possible {1 1 2 2 3 3 4 4 5 5 6 6})
; now try to add another set of values with d6-possible to see the range of combinations

(defn gsword []
  ; the combinatorics of 2 die with 6 sides
  (d6-possible 1
  ))


;; d&d related

(defn die[sides]
  ; an arbitrary die rolling function
  (inc (rand-int sides)))
  
(defn statgen [stat]
  ; makes d20 stats
  (+ (die 6)(die 6)(die 6))
  ; how did I allow this function to name a map / piece of data for 'stat'?
  )

(defn rig [cls]
  ; a d20 random individual generator
  (def hp {:adept 6 :commoner 4 :cleric 8 :bard 6 :barbarian 12 :druid 8 :monk 8 :fighter 10 :paladin 10 :ranger 8 :rogue 6 :sorcerer 4 :wizard 4 :psion 4 :psywar 8 :wilder 6 :soulknife 10})
  ; had trouble getting let to work - I know def is evil
  (let [abils {:stre nil :dex nil :con nil :inte nil :wis nil :cha nil}])
  (statgen "strength") ; these won't matter until I get the damn function returning a value somewhere
  (statgen "dexterity")
  (statgen "constitution")
  (statgen "intelligence")
  (statgen "wisdom")
  (statgen "charisma")
  (println "Health = " (inc(rand-int(get cls hp))))
  )

; look