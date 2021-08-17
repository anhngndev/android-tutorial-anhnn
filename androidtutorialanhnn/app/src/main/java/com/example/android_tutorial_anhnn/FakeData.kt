package com.example.android_tutorial_anhnn

import com.example.android_tutorial_anhnn.data.model.Icon

object FakeData {
    fun getIcons():MutableList<Icon>{
        var list: MutableList<Icon> = mutableListOf()
        var icon1 = Icon("App Store", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fapp-store.png?alt=media&token=18a08487-b0d5-448d-8f0e-d48ceda0a1e5")
        var icon2 = Icon("Behance", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fbehance.png?alt=media&token=d57445f0-075c-419d-8a4a-046fc6b1d39d")
        var icon3 = Icon("Books", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fbooks.png?alt=media&token=c0ff568d-c8b1-423a-a163-1d120f7ca1c3")
        var icon4 = Icon("Facebook", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Ffacebook.png?alt=media&token=4149cdf9-02b9-4c8d-aed2-1325d83fe083")
        var icon5 = Icon("Been Together", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Flove.png?alt=media&token=79bf2635-d6f6-497d-a640-a1399e8ac3bd")
        var icon6 = Icon("Map", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fmap.png?alt=media&token=a187a7fc-cc7f-4e17-8cb2-9e9d4f3241e0")
        var icon7 = Icon("Pinterest", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fpinterest.png?alt=media&token=625cb46c-1d52-4287-8483-d1c4fc4da022")
        var icon8 = Icon("Earth", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fplanet-earth.png?alt=media&token=603e16df-1c56-42cb-9a8e-c63780005fea")
        var icon9 = Icon("Play Store", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fplay.png?alt=media&token=3f6de1c4-ee3d-4ee4-9aff-27192ae4b0b4")
        var icon10 = Icon("Safari", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fsafari.png?alt=media&token=c1788354-a900-4a86-ad2a-5b462c50c310")
        var icon11 = Icon("Google", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fsearch.png?alt=media&token=a732b8d3-985a-4b72-a7c5-b0f207e84c76")
        var icon12 = Icon("Telegram", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Ftelegram.png?alt=media&token=213bb2b1-7015-4bf6-8e1b-b6198ee556d5")
        var icon13 = Icon("Twitter", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Ftwitter.png?alt=media&token=7ddc7a65-cf23-4331-9648-71bc255934b9")
        var icon14 = Icon("Viber", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fviber.png?alt=media&token=525cac43-7db6-49ab-a98e-5801537d5de5")
        var icon15 = Icon("Whats app", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/AppIcon%2Fwhatsapp.png?alt=media&token=ca2cf3bf-528c-4cf1-936d-58a8924ba673")
        var icon16 = Icon("Pet", "https://firebasestorage.googleapis.com/v0/b/myfood-2b764.appspot.com/o/Icon%2Fworried.png?alt=media&token=0c9467a3-23b5-430f-962e-65aa9a87bf57")

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