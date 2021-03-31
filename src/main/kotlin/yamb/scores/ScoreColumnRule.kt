package yamb.scores

interface ScoreColumnRule {

    fun getAvailableIndices(column : Collection<Int>) : Collection<Int>

}