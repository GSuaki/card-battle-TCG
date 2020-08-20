package card.battle.game

import card.battle.model.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("unitTest")
class GamesTest {

    @Test
    fun `test new game`() {
        val game = Games.newGame(arrayOf("Red", "Blue"))

        assertNotNull(game.id)
        assertEquals(Player.newPlayer("Red"), game.players.first())
        assertEquals((Player.newPlayer("Blue")), game.players.last())
        assertNull(game.winner)
    }
}