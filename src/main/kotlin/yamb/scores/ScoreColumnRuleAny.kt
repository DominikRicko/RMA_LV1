package yamb.scores

object ScoreColumnRuleAny : ScoreColumnRule {
    override fun getAvailableIndices(column: Collection<Score>): Collection<Int> {

        val indices = arrayListOf<Int>()

        column.forEachIndexed { i, score ->
            if (score.value == Score.NO_SCORE_VALUE)
                indices.add(i)
        }

        return indices

    }

}