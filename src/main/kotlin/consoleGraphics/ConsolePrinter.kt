package consoleGraphics

import data.Observer

object ConsolePrinter : Printer, Observer<Displayable> {

    override fun printDisplayable(displayable: Displayable) {
        println(displayable.getDisplayStringSet())
    }

    override fun update(observableType: Displayable) {
        printDisplayable(observableType)
    }
}