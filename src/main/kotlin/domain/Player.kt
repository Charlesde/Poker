package domain

data class Player(
    val id: Int,
    val name: String,
    val blind: Blind = Blind.NO_BLIND,
    val pocket: List<Card> = emptyList(),
    val chips: List<Chip> = emptyList(),
    val isDealer: Boolean = false,
    val isWinner: Boolean = false,
) {
    //    It bullies me about not initialising so I put in bullshit numbers
    var hand: HighestHand = HighestHand(1,1, listOf(1,1),1)

    fun switchDealer() = !isDealer
}
