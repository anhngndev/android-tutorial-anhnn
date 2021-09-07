package ai.ftech.dev.base.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<BaseAdapter.BaseVH<Any>>() {

    companion object {
        const val INVALID_RESOURCE = -1
    }

    var dataList: MutableList<Any> = mutableListOf()
        private set

    abstract fun getLayoutResource(viewType: Int): Int

    abstract fun onCreateViewHolder(viewType: Int, binding: ViewDataBinding): BaseVH<*>

    abstract fun getDataAtPosition(position: Int): Any

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<Any> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                getLayoutResource(viewType),
                parent,
                false
        ).apply {
            val context = root.context as LifecycleOwner
            lifecycleOwner = context
            executePendingBindings()
        }
        return onCreateViewHolder(viewType, binding) as BaseVH<Any>
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseVH<Any>, position: Int) {
        holder.onBind(getDataAtPosition(position))
    }

    override fun onBindViewHolder(
            holder: BaseVH<Any>,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onBind(getDataAtPosition(position), payloads)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetData(dataList: List<Any>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addListItem(dataList: List<Any>) {
        val size = this.dataList.size
        this.dataList.addAll(dataList)
        notifyItemRangeInserted(size, dataList.size)
    }

    fun add(item: Any) {
        this.dataList.add(item)
        notifyItemInserted(dataList.size)
    }

    fun add(item: Any, position: Int) {
        this.dataList.add(item)
        notifyItemInserted(position)
    }

    open class BaseVH<T>(itemBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        open fun onBind(data: T) {}
        open fun onBind(data: T, payloads: MutableList<Any>) {}
    }
}
