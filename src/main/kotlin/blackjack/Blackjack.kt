package blackjack

import Game
import cards.Card
import cards.MutableDeck
import consoleGraphics.Displayable
import data.Observable
import data.Observer

class Blackjack(private val rounds : Int) : Game, Observable<Displayable>, Displayable {

    private val players : ArrayList<BlackjackPlayer> = arrayListOf()
    private val deck = MutableDeck()

    private enum class GameState{
        ROUND_START,
        TURN_START,
        TURN_END,
        ROUND_END
    }

    private var gameState : GameState = GameState.ROUND_START
    private var startingDeckSize : Int = 0
    private lateinit var currentPlayer : BlackjackPlayer

    init{

        enumValues<Card.Rank>().forEach { rank ->
            enumValues<Card.Suit>().forEach{ suit ->
                deck.addCard(Card(rank,suit))
            }
        }

    }

    override fun start() {

        for(round in 0 until rounds step 1){

            deck.shuffle()

            startingDeckSize = deck.getSize()

            players.forEach {
                dealCard(it)
                dealCard(it)
            }

            val roundPlayers = arrayListOf<BlackjackPlayer>()
            players.toCollection(roundPlayers)

            gameState = GameState.ROUND_START

            while(gameState != GameState.ROUND_END){

                for(i in 0 until roundPlayers.size step 1){

                    currentPlayer = roundPlayers[i]
                    gameState = GameState.TURN_START

                    while(gameState != GameState.TURN_END){

                        currentPlayer.processNextCommand()

                        if(currentPlayer.getHandScore() >= 21) {

                            roundPlayers.removeAt(i)
                            gameState = GameState.TURN_END

                        }
                    }
                }

                determineIfRoundEnd()

            }

            val leaderboard = players.groupBy { it.getHandScore() }.toSortedMap()
            declareVictors(leaderboard[leaderboard.lastKey()]!!)

            players.forEach { player -> player.returnCards().forEach { card -> deck.addCard(card) } }

        }

    }

    private fun declareVictors(victors: List<BlackjackPlayer>) {
        if (victors.size > 1)
            TODO("Stalemate")
        else
            TODO("Not yet implemented")
    }

    private fun determineIfRoundEnd() {
        if(startingDeckSize == deck.getSize())
            gameState = GameState.ROUND_END
    }

    override fun addPlayer(playerName: String, playerType: Game.PlayerType) {
        players.add(when(playerType){
            Game.PlayerType.USER -> BlackjackUserPlayer(playerName,this)
            Game.PlayerType.BASIC_AI -> BlackJackBasicAIPlayer(playerName, this)
            Game.PlayerType.RANDOM_AI -> TODO()
        })
    }

    fun dealCard(player: BlackjackPlayer) {
        player.giveCard(deck.drawCard())
    }

    fun endTurn() {
        gameState = GameState.TURN_END
    }

    override fun getDisplayStringSet(): String {
        var outputString = "Player: $currentPlayer\nHand: "

        currentPlayer.hand.forEach { outputString += "${it.rank} of ${it.suit}\n"}

        outputString += "\nScore: ${currentPlayer.getHandScore()}" +
                "Command are \"hit\" and \"stand\"\n\n\n"

        return outputString
    }

    private val subscribers : ArrayList<Observer<Displayable>> = arrayListOf()

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