package blackjack

import cards.Card

class BlackJackBasicAIPlayer(override val name: String, override val game: Blackjack) : BlackjackPlayer {
    override val hand: Collection<Card>
        get() = TODO("Not yet implemented")

    override fun getHandScore(): Int {
        TODO("Not yet implemented")
    }

    override fun giveCard(card: Card) {
        TODO("Not yet implemented")
    }

    override fun returnCards(): Collection<Card> {
        TODO("Not yet implemented")
    }

    override fun processNextCommand() {
        TODO("Not yet implemented")
    }

}
