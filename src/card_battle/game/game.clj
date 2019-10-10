(ns card-battle.game.game
  (:require [clojure.tools.logging :as log]
            [card-battle.game.player :as players]
            [card-battle.game.utils :as utils]))

(defn winner?
  "Check if we have a Winner!"
  [{{one :one, two :two} :players}]
  (or (<= (:health one) 0)
      (<= (:health two) 0)))

(defn switch-turn [game]
  (update-in game [:active-player] #(if (= %1 :one) :two :one)))

(defn fulfill-player-mana
  [{active-player :active-player, :as game}]
  (update-in game [:players active-player] players/fulfill-mana))

(defn draw-new-card
  [{active-player :active-player, :as game}]
  (update-in game [:players active-player] players/draw-new-card))

(defn make-player-moves
  [{active-player :active-player, :as game}]
  (update-in game [:players] #((players/make-moves %1 active-player))))

(defn start-turn [game]
  (Thread/sleep 1000)
  (println "Starting turn: " (get-in game [:players (:active-player game)]))
  (-> game
      (fulfill-player-mana)
      (draw-new-card)
      (make-player-moves)
      (switch-turn)
      (start-turn)))

(defn sort-first-player [game]
  (assoc-in game [:active-player] (rand-nth [:one :two])))

(defn draw-players-hand
  [{players :players, active :active-player, :as game}]
  (assoc-in game [:players] (players/draw-players-hand players active)))

(defn start-game
  "Start game"
  [game]
  (log/info "Starting: " (:id game))
  (-> game
      (sort-first-player)
      (draw-players-hand)
      (start-turn)))

(defn create-game
  "Create game structure"
  [names]
  (log/info "Creating game structure")
  {:id (utils/uuid), :players (players/generate-players names)})

(defn new-game
  "Create new game"
  [players-name]
  (log/info "Creating new game")
  (create-game players-name))