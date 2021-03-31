package yamb.scores

interface ScoreColumnRule {

    fun getAvailableIndices(column : Collection<Score>) : Collection<Int>

}