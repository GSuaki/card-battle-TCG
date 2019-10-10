(ns card-battle.game.utils
  (:import (java.util UUID)))

(defn uuid [] (.toString (UUID/randomUUID)))