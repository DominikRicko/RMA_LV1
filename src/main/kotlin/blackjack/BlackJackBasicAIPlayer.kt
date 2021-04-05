package blackjack

import cards.Card

class BlackJackBasicAIPlayer(override val name: String, override val game: Blackjack) : BlackjackPlayer {

    override val hand : ArrayList<Card> = arrayListOf()

    override fun getHandScore(): Int {

        var sum = 0
        var aceCount = 0

        hand.forEach {

            when {
                it.rank.value > 10 -> sum += 10
                it.rank == Card.Rank.Ace -> aceCount++
                else -> sum += it.rank.value
            }

        }

        for(i in 0 until aceCount){

            if(sum + 11 > 21)
                sum++
            else
                sum += 11
        }

        return sum
    }

    override fun giveCard(card: Card) {
        hand.add(card)
    }

    override fun returnCards(): Collection<Card> {
        val cards = arrayListOf<Card>()

        hand.toCollection(cards)
        hand.clear()

        return cards
    }

    override fun processNextCommand() {

        val currentScore = getHandScore()

        if(currentScore <= 16)
            game.dealCard(this)
        else
            game.endTurn()

    }
    override fun toString(): String {
        return name
    }

}
