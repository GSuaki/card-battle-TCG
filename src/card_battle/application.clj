(ns card-battle.application
  (:require [clojure.tools.logging :as log]
            [card-battle.game.game :as game])
  (:gen-class))

; {:active-player :one, :players {:one {:name "Gabriel"}, :two {:name "Gabriel"}}}
(defn -main
  "The entry-point for 'lein run'"
  [& args]
  (log/info "Starting server...")
  (let [p1 (do (print "Qual o nome do jogador 1? ") (flush) (read-line))
        p2 (do (print "Qual o nome do jogador 2? ") (flush) (read-line))
        new-game (game/new-game [p1 p2])]
    (log/info (game/start-game new-game))))