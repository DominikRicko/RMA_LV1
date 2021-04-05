package blackjack.player

import blackjack.Blackjack
import cards.Card

interface BlackjackPlayer {

    val name: String
    val game: Blackjack
    val hand: Collection<Card>

    fun getHandScore(): Int
    fun giveCard(card: Card)
    fun returnCards(): Collection<Card>

    fun processNextCommand()

}