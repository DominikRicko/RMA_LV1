package blackjack

import cards.Card
import consoleGraphics.Displayable

interface BlackjackPlayer : Displayable {

    val name : String
    val game : Blackjack

    fun getHandScore() : Int
    fun giveCard(card: Card)
    fun returnCards() : Collection<Card>

    fun processNextCommand()

}