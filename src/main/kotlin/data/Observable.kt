package data

interface Observable<T> {
    fun subscribe(observer : Observer<T>)
    fun notifyObservers()
}