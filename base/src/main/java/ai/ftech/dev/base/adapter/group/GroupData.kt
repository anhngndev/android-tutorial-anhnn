package ai.ftech.dev.base.adapter.group

import ai.ftech.dev.base.adapter.BaseVH
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class GroupData<DATA>(
    var data: DATA
) {

    companion object {
        const val INVALID_RESOURCE = -1
    }

    var positionInGroup = -1

    var groupManager: GroupManager? = null

    abstract fun getItemViewType(position: Int): Int

    abstract fun getDataInGroup(positionInGroup: Int): Any

    @LayoutRes
    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun getCount(): Int

    abstract fun onCreateVH(itemViewBinding: ViewDataBinding, viewType: Int): BaseVH<*>?

    open fun attach() {
        positionInGroup = groupManager?.findAdapterPositionForGroup(this) ?: -1
    }

    open fun detach() {
        positionInGroup = -1
    }

    open fun isAttached(): Boolean {
        return positionInGroup > -1
    }

    open fun notifyRemove(groupPosition: Int) {
        if (isAttached()) {
            groupManager?.notifyRemove(this, positionInGroup + groupPosition)
        }
    }

    open fun notifyRemove(groupPosition: Int, count: Int) {
        if (isAttached()) {
            groupManager?.notifyRemove(this, positionInGroup + groupPosition, count)
        }
    }

    open fun notifyInserted(groupPosition: Int, count: Int) {
        if (count <= 0) {
            return
        }

        if (!isAttached()) {
            attach()
        }
        groupManager?.notifyInserted(this, positionInGroup + groupPosition, count)
    }

    open fun notifyChanged(groupPosition: Int) {
        if (!isAttached()) {
            attach()
        }
        groupManager?.notifyChanged(this, positionInGroup + groupPosition)
    }

    open fun notifyChanged(groupPosition: Int, payload: Any?) {
        if (!isAttached()) {
            attach()
        }
        groupManager?.notifyChanged(this, positionInGroup + groupPosition, payload)
    }

    open fun notifyChanged() {
        groupManager?.notifyChanged(this)
    }

    open fun notifyDataSetChanged() {
        groupManager?.notifyDataSetChanged()
    }

    open fun notifyChange(payload: Any?) {
        groupManager?.notifyChanged(this, payload)
    }

    open fun notifySelfInserted() {
        groupManager?.notifyNewGroupAdded(this)
    }

    open fun notifySelfRemoved() {
        groupManager?.removeGroup(this)
    }

    open fun mapAdapterPositionToGroupPosition(adapterPosition: Int): Int {
        return adapterPosition - this.positionInGroup
    }

    open fun getAdapterPositionFromGroupPosition(groupPosition: Int): Int {
        return positionInGroup + groupPosition
    }
}
