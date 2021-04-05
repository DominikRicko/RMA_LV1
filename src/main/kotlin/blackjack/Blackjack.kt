package blackjack

import Game
import blackjack.player.BlackJackBasicAIPlayer
import blackjack.player.BlackjackPlayer
import blackjack.player.BlackjackUserPlayer
import cards.Card
import cards.MutableDeck
import consoleGraphics.Displayable
import data.Observable
import data.Observer

class Blackjack(private val rounds: Int) : Game, Observable<Displayable>, Displayable {

    private val players: ArrayList<BlackjackPlayer> = arrayListOf()
    private val deck = MutableDeck()

    private enum class GameState {
        ROUND_START,
        TURN_START,
        TURN_END,
        ROUND_END
    }

    private var gameState: GameState = GameState.ROUND_START
    private var startingDeckSize: Int = 0
    private lateinit var currentPlayer: BlackjackPlayer

    init {

        enumValues<Card.Rank>().forEach { rank ->
            enumValues<Card.Suit>().forEach { suit ->
                deck.addCard(Card(rank, suit))
            }
        }

    }

    override fun start() {

        for (round in 0 until rounds step 1) {

            deck.shuffle()

            players.forEach {
                dealCardWithoutObserverNotification(it)
                dealCardWithoutObserverNotification(it)
            }

            val roundPlayers = arrayListOf<BlackjackPlayer>()
            players.toCollection(roundPlayers)

            gameState = GameState.ROUND_START

            while (gameState != GameState.ROUND_END) {

                startingDeckSize = deck.getSize()

                var i = 0
                while (i < roundPlayers.size) {

                    currentPlayer = roundPlayers[i]
                    gameState = GameState.TURN_START

                    while (gameState != GameState.TURN_END) {

                        notifyObservers()
                        currentPlayer.processNextCommand()

                        if (currentPlayer.getHandScore() >= 21) {

                            roundPlayers.removeAt(i)
                            i--
                            gameState = GameState.TURN_END


                        }
                    }
                    i++
                }

                determineIfRoundEnd()

            }


            declareVictors(determineVictors())

            players.forEach { player -> player.returnCards().forEach { card -> deck.addCard(card) } }

        }

    }

    private fun determineVictors(): Collection<BlackjackPlayer> {

        val leaderboard = players.filter { it.getHandScore() <= 21 }.groupBy { it.getHandScore() }.toSortedMap()

        val victors = arrayListOf<BlackjackPlayer>()

        if (leaderboard.size == 0) return victors

        leaderboard[leaderboard.lastKey()]!!.forEach { victors.add(it) }

        return victors

    }

    private fun declareVictors(victors: Collection<BlackjackPlayer>) {
        when {
            victors.size > 1 -> {

                var output = "Stalemate between players:"
                victors.forEach { output += " $it" }
                output += "."
                println(output)
            }

            victors.size == 1 -> println("Player ${victors.first()} wins!")
            else -> println("No victors")
        }
    }

    private fun determineIfRoundEnd() {
        if (startingDeckSize == deck.getSize())
            gameState = GameState.ROUND_END
    }

    override fun addPlayer(playerName: String, playerType: Game.PlayerType) {
        players.add(
            when (playerType) {
                Game.PlayerType.USER -> BlackjackUserPlayer(playerName, this)
                Game.PlayerType.BASIC_AI -> BlackJackBasicAIPlayer(playerName, this)
                Game.PlayerType.RANDOM_AI -> TODO()
            }
        )
    }

    private fun dealCardWithoutObserverNotification(player: BlackjackPlayer) {
        player.giveCard(deck.drawCard())
    }

    fun dealCard(player: BlackjackPlayer) {
        dealCardWithoutObserverNotification(player)
        notifyObservers()
    }

    fun endTurn() {
        gameState = GameState.TURN_END
    }

    override fun getDisplayStringSet(): String {
        var outputString = "\n\nPlayer: $currentPlayer\nHand:\n"

        currentPlayer.hand.forEach { outputString += "${it.rank} of ${it.suit}\n" }

        outputString += "\nScore: ${currentPlayer.getHandScore()}\n" +
                "Command are \"hit\" and \"stand\"\n\n\n"

        return outputString
    }

    private val subscribers: ArrayList<Observer<Displayable>> = arrayListOf()

    override fun subscribe(observer: Observer<Displayable>) {
        subscribers.add(observer)
    }

    override fun unsubscribe(observer: Observer<Displayable>) {
        subscribers.remove(observer)
    }

    override fun notifyObservers() {
        subscribers.forEach { it.update(this) }
    }
}