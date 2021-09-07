package ai.ftech.dev.base.common

import android.graphics.Color

const val LAYOUT_INVALID = -1

interface Config {
    fun getLayoutResource(): Int
}

interface ActivityConfig : Config {
    fun isFixSingleTask(): Boolean = false
    fun isOnlyPortraitScreen(): Boolean = false
    fun getContainerId(): Int = LAYOUT_INVALID
}

interface FragmentConfig : Config {
    fun setupStatusBar(): StatusBar = StatusBar()
    fun isAttachMenuToFragment(): Boolean = true
    fun isHandleBackPressByFragment(): Boolean = true
    fun getContainerId(): Int = LAYOUT_INVALID
}

interface DialogConfig : Config {
    fun getBackgroundId(): Int
    fun screen(): DialogScreen = DialogScreen()
}

class StatusBar {
    var color: Int = Color.TRANSPARENT
    var isDarkText: Boolean = true
}

class DialogScreen {
    var mode: DIALOG_MODE = DIALOG_MODE.NORMAL
    var isFullWidth: Boolean = false
    var isFullHeight: Boolean = false
    var isDismissByTouchOutSide: Boolean = true
    var isDismissByOnBackPressed: Boolean = true

    enum class DIALOG_MODE {
        NORMAL,
        BOTTOM
    }
}
