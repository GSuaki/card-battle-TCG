(ns card-battle.game.player
  (:require [clojure.tools.logging :as log]))

(defn fulfill-mana
  "Fulfill player mana"
  [{current-slots :manaSlots, :as player}]
  (let [new-slots (if (< current-slots 10) (inc current-slots) 10)]
    (assoc player
      :manaSlots new-slots
      :mana new-slots)))

(defn draw-initial-hand
  "Draw player initial hand"
  [player cards]
  (assoc player
    :hand (take cards (:cards player))
    :cards (drop cards (:cards player))))

(defn draw-new-card
  "Draw player initial hand"
  [{cards :cards, :as player}]
  (assoc player :hand (take 1 cards) :cards (drop 1 cards)))

(defn make-moves
  "Draw players initial hand"
  [players active-player]
  (-> players
      (assoc-in [:one] (draw-initial-hand (:one players) (if (= active-player :one) 3 4)))
      (assoc-in [:two] (draw-initial-hand (:two players) (if (= active-player :two) 3 4)))))

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