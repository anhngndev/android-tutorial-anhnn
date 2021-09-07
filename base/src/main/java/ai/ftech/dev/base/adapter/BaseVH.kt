package ai.ftech.dev.base.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseVH<DATA>(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    open fun onBind(vhData: DATA) {}
    open fun onBind(vhData: DATA, payloads: MutableList<Any>) {}
}
