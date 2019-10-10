(ns card-battle.game.game
  (:require [clojure.tools.logging :as log]
            [card-battle.game.player :as players]
            [card-battle.game.utils :as utils]))

(defn find-winner
  "Find the opposite player that have 0 of health"
  [{{one :one, two :two} :players}]
  (cond (<= (:health one) 0) two
        (<= (:health two) 0) one
        :else nil))

(defn winner?
  "Check if we have a Winner!"
  [game]
  (not (nil? (find-winner game))))

(defn announce-winner
  [game]
  (assoc game :winner (find-winner game)))

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
  (update-in game [:players] (fn [pls] (players/make-moves pls active-player))))

(defn start-turn [game]
  (Thread/sleep 100)
  (println "Starting turn: " (get-in game [:players (:active-player game)]))
  (cond (winner? game) (announce-winner game)
        :else (-> game
                  (fulfill-player-mana)
                  (draw-new-card)
                  (make-player-moves)
                  (switch-turn)
                  (start-turn))))

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