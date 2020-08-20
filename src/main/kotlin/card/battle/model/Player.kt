package card.battle.model

data class Player(
    val name: String,
    val cards: List<Int>,
    val hand: List<Int>,
    val health: Int,
    val manaSlots: Int,
    val mana: Int
) {

    companion object {

        private fun createInitialCards(): List<Int> =
            listOf(0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 7, 8);

        fun newPlayer(name: String): Player = Player(
            name = name,
            hand = listOf(),
            cards = createInitialCards(),
            health = 30,
            manaSlots = 0,
            mana = 0
        )
    }
}