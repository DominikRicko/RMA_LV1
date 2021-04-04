interface Game {
    fun start()
    fun addPlayer(playerName: String, playerType: PlayerType)

    enum class PlayerType {
        RANDOM_AI,
        USER
    }
}