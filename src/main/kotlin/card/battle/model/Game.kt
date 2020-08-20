package card.battle.model

import java.util.*

data class Game(
    val id: UUID,
    val players: List<Player>,
    val winner: Player? = null
) {

    companion object {

        fun withPlayers(names: Array<String>): Game =
            Game(id = UUID.randomUUID(), players = names.map { Player.newPlayer(it) })
    }
}