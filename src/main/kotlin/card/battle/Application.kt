package card.battle

import card.battle.game.newGame
import card.battle.game.startGame
import card.battle.model.Game

val printWinner: (Game) -> Unit = { print("We have a Winner! Congratulations: ${it.winner?.name}") }

private fun readPlayer(number: Int): String {
    println("Qual o nome do jogador $number? ")
    return readLine() ?: throw RuntimeException("Nome do jogador é obrigatório")
}

fun main() {

    println("Olá, vamos jogar Card Battle!")

    val p1 = readPlayer(1)
    val p2 = readPlayer(2)

    newGame(players = arrayOf(p1, p2))
        .run(::startGame)
        .run(printWinner)
}