(ns card-battle.game.game-test
  (:require [clojure.test :refer :all]
            [card-battle.game.game :refer :all]))

(deftest test-create-game
  (testing "Create game structure")
  (let [actual (create-game ["Gabriel", "Priscila"])]
    (print actual)
    (is (not= nil (:id actual)))
    (is (= "Gabriel" (:name (:one (:players actual)))))
    (is (= "Priscila" (:name (:two (:players actual)))))))