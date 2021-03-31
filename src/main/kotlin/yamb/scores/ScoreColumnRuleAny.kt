package yamb.scores

object ScoreColumnRuleAny : ScoreColumnRule{
    override fun getAvailableIndices(column: Collection<Int>): Collection<Int> {

        val indices = arrayListOf<Int>()

        column.forEachIndexed{i, value ->
            if(value == -1)
                indices.add(i)
        }

        return indices

    }

}