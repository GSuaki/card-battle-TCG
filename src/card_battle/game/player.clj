(ns card-battle.game.player
  (:require [clojure.tools.logging :as log]))

(def desc #(compare %2 %1))

(defn remove-once [pred coll]
  ((fn inner [coll]
     (lazy-seq
       (when-let [[x & xs] (seq coll)]
         (if (pred x)
           xs
           (cons x (inner xs))))))
   coll))

(defn fulfill-mana
  "Fulfill player mana"
  [{current-slots :manaSlots, :as player}]
  (let [new-slots (if (< current-slots 10) (inc current-slots) 10)]
    (assoc player
      :manaSlots new-slots
      :mana new-slots)))

(defn draw-initial-hand
  "Draw player initial hand, taking from the deck of cards to players hand"
  [{cards :cards, :as player} cards-length]
  (assoc player
    :hand (take cards-length cards)
    :cards (drop cards-length cards)))

(defn draw-new-card
  "Draw new card from deck based on conditions:
  1. If doesn't have any cards on deck, apply bleedout law.
  2. If already has 5 cards on hand, discard the new card
  3. Else get one card from deck to players hand"
  [{cards :cards, hand :hand, health :health, :as player}]
  (cond (= (count cards) 0) (assoc player :health (dec health))
        (= (count hand) 5) (assoc player :cards (rest cards))
        :else (assoc player :hand (conj hand (first cards)) :cards (rest cards))))

(defn can-afford-any?
  "Loop through the col filtering cards that costs less then the mana"
  [cards mana]
  (not (empty? (filter #(<= % mana) cards))))

(defn play-cards
  "Play card on the hand if while can afford"
  [{hand :hand, mana :mana, name :name, :as active}, {health :health, :as opponent}]
  (if (can-afford-any? hand mana)
    (let [player-hand (sort desc hand)
          affordable-cards (sort desc (filter #(<= % mana) player-hand))
          card (first affordable-cards)]

      (log/info "New attack incoming: " name)
      (log/info "Hand: " player-hand ", Affordable cards: " affordable-cards ", Mana: " mana ", Damage: " card)

      (play-cards
        (assoc active :hand (remove-once #(= % card) player-hand) :mana (- mana card))
        (assoc opponent :health (- health card))))
    {:active active :opponent opponent}))

(defn make-moves
  "Make all affordable movies with the hand of the player"
  [players active-id]
  (let [opponent-id (if (= active-id :one) :two :one)
        player (active-id players)
        opponent (opponent-id players)
        {p1 :active, p2 :opponent} (play-cards player opponent)]
    {active-id p1, opponent-id p2}))

(defn draw-players-hand
  "Draw players initial hand"
  [players active-player]
  (-> players
      (assoc-in [:one] (draw-initial-hand (:one players) (if (= active-player :one) 3 4)))
      (assoc-in [:two] (draw-initial-hand (:two players) (if (= active-player :two) 3 4)))))

(defn create-initial-cards []
  (shuffle (shuffle (shuffle (list 0 0 1 1 2 2 2 3 3 3 3 4 4 4 5 5 6 6 7 8)))))

(defn create-player
  "Create player structure"
  [name]
  (log/info "Creating player structure: " name)
  {:name name, :cards (create-initial-cards), :health 30, :manaSlots 0, :mana 0})

(defn generate-players
  "Generate players based on quantity"
  [names]
  {:one (create-player (first names)), :two (create-player (last names))})