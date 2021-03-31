package data

class Matrix<T>(val sizeX: Int, val sizeY: Int, private val array: Array<T>) {

    companion object {

        inline operator fun <reified T> invoke() = Matrix(0, 0, emptyArray<T>())

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int) =
            Matrix(xWidth, yWidth, arrayOfNulls<T>(xWidth*yWidth))

        inline operator fun <reified T> invoke(xWidth: Int, yWidth: Int, initValue: () -> (T)): Matrix<T> {
            val array = Array(xWidth*yWidth) {
                return@Array initValue()
            }
            return Matrix(xWidth, yWidth, array)
        }
    }

    operator fun get(x: Int, y: Int) : T {
        return array[sizeX * y + x]
    }

    operator fun set(x : Int, y: Int, value : T){
        array[sizeX * y + x] = value
    }

    fun getRow(y : Int) : Collection<T>{

        val arrayList : ArrayList<T> = ArrayList()

        for( i in y*sizeX..(y+1)*sizeX)
            arrayList.add(array[i])

        return arrayList
    }

    fun getColumn(x : Int) : Collection<T>{

        val arrayList : ArrayList<T> = ArrayList()

        for( i in x..sizeY*sizeX step sizeX)
            arrayList.add(array[i])

        return arrayList
    }

}