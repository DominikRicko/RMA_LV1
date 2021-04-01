import yamb.Yamb

fun main() {
    val game = Yamb(4)

    game.addPlayer("Dominik", Game.PlayerType.USER)
    game.addPlayer("Dominik", Game.PlayerType.RANDOM_AI)

    game.start()
}