(ns card-battle.game.utils-test
  (:require [clojure.test :refer :all]
            [card-battle.game.utils :refer :all]))

(deftest uuid-test
  []
  (testing "UUID generation")
  (is (not-empty (uuid))))