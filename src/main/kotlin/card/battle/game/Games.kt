package card.battle.game

import card.battle.model.Game

fun newGame(players: Array<String>): Game =
    Game.withPlayers(names = players)

fun startGame(game: Game): Game =
    game.copy(winner = game.players.first())