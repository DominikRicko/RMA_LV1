package yamb.scores

object ScoreColumnRuleBottomUp : ScoreColumnRule {
    override fun getAvailableIndices(column: Collection<Score>): Collection<Int> {

        return listOf(column.indexOfLast { it.value == Score.NO_SCORE_VALUE })

    }
}