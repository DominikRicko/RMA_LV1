package data

interface Observer<T> {
    fun update(observableType: T)
}