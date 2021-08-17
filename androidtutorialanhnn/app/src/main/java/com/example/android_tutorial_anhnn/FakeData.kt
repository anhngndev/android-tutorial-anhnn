package com.example.android_tutorial_anhnn

import com.example.android_tutorial_anhnn.data.model.Icon

object FakeData {
    fun getIcons():MutableList<Icon>{
        var list: MutableList<Icon> = mutableListOf()
        var icon1 = Icon("Icon1", "R.drawable.ic_amazed")
        var icon2 = Icon("Icon2", "R.drawable.ic_angel")
        var icon3 = Icon("Icon3", "R.drawable.ic_bored")
        var icon4 = Icon("Icon4", "R.drawable.ic_cool")
        var icon5 = Icon("Icon5", "R.drawable.ic_cry")
        var icon6 = Icon("Icon6", "R.drawable.ic_devil")
        var icon7 = Icon("Icon7", "R.drawable.ic_dizzy")
        var icon8 = Icon("Icon8", "R.drawable.ic_furious")
        var icon9 = Icon("Icon9", "R.drawable.ic_grimacing")
        var icon10 = Icon("Icon10", "R.drawable.ic_happy")
        var icon11 = Icon("Icon11", "R.drawable.ic_kiss")
        var icon12 = Icon("Icon12", "R.drawable.ic_amazed")
        var icon13 = Icon("Icon13", "R.drawable.ic_puke")
        var icon14 = Icon("Icon14", "R.drawable.ic_sleeping")
        var icon15 = Icon("Icon15", "R.drawable.ic_worried")
        var icon16 = Icon("Icon16", "R.drawable.ic_thinking")

        list.add(icon1)
        list.add(icon2)
        list.add(icon3)
        list.add(icon4)
        list.add(icon5)
        list.add(icon6)
        list.add(icon7)
        list.add(icon8)
        list.add(icon9)
        list.add(icon10)
        list.add(icon11)
        list.add(icon12)
        list.add(icon13)
        list.add(icon14)
        list.add(icon15)
        list.add(icon16)

        return list
    }
}