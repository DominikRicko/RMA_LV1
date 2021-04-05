import blackjack.Blackjack
import consoleGraphics.ConsolePrinter
import yamb.Yamb

val playerNames = arrayListOf<String>()
val playerTypes = arrayListOf<Game.PlayerType>()

fun instantTest(){
    val game = Blackjack(4)

    game.addPlayer("Dominik", Game.PlayerType.USER)
    game.addPlayer("AI", Game.PlayerType.BASIC_AI)

    game.subscribe(ConsolePrinter)

    game.start()
}

fun main() {

    displayHelp()

    instantTest()

    while(true){
        val input = readLine()?.split(' ') ?: continue

        when(input[0]){
            "add_player" -> addPlayer(input[1], input[2])
            "start_yamb" -> startYamb(input[1])
            "start_blackjack" -> startBlackjack(input[1])
            "help" -> displayHelp()
        }

    }

}

fun startBlackjack(roundsString: String) {

    var rounds = 0
    if (runCatching { rounds = roundsString.toInt() }.isFailure){
        println("Error parsing rounds amount")
        return
    }

    if(rounds > 0){
        println("Rounds cannot be lower than 1")
    }

    val game = Blackjack(rounds)

    for(i in 0 until playerNames.size step 1){
        game.addPlayer(playerNames[i], playerTypes[i])
    }

    game.start()

}

fun startYamb(roundsString : String) {

    var rounds = 0
    if (runCatching { rounds = roundsString.toInt() }.isFailure){
        println("Error parsing rounds amount")
        return
    }

    if(rounds > 0){
        println("Rounds cannot be lower than 1")
    }

    val game = Yamb(rounds)

    game.subscribe(ConsolePrinter)

    for( i in 0 until playerNames.size step 1){
        game.addPlayer(playerNames[i], playerTypes[i])
    }

    game.start()

}

fun displayHelp() {
    println("Commands:")
    println("\t\tadd_player [playerName] [playerType] - is used to add players for games")
    println("\t\t\tpossible arguments for playerType are: user, randomAI, basicAI")
    println("\t\tstart_yamb [rounds] - starts the yamb game with previously defined players")
    println("\t\tstart_blackjack -starts the blackjack game with previously defined players")
    println("\nNote: Yamb does not have a BasicAI implemented, and Blackjack doesn't have a RandomAI implemented.\n")
}

fun addPlayer(playerName: String, playerTypeString: String) {

    val playerType = when(playerTypeString){
        "user" -> Game.PlayerType.USER
        "randomAI" -> Game.PlayerType.RANDOM_AI
        "basicAI" -> Game.PlayerType.BASIC_AI
        else -> null
    }

    if(playerType == null){
        println("Error parsing player type")
        return
    }

    if(playerNames.contains(playerName)){
        println("Cannot have two players with same name")
        return
    }

    playerNames.add(playerName)
    playerTypes.add(playerType)

}
