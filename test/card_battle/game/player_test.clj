(ns card-battle.game.player-test
  (:require [clojure.test :refer :all]
            [card-battle.game.player :refer :all]))

(deftest test-create-player
  (testing "Create game structure")
  (let [expected {:name "Gabriel", :health 30, :manaSlots 0, :mana 0}
        actual (dissoc (create-player "Gabriel") :cards)]
    (is (= expected actual))))