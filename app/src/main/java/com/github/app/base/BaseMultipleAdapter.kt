package com.github.app.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.ext.binding.initializePageWidth
import java.io.Serializable

abstract class BaseMultipleAdapter<T : Serializable, VH : RecyclerView.ViewHolder>(
    protected val mList: MutableList<T> = ArrayList()
) : RecyclerView.Adapter<VH>() {

    @SuppressWarnings("unused")
    lateinit var getContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View = LayoutInflater.from(parent.context).inflate(getResources(viewType), parent, false).apply {
            setContext(context)
            initializePageWidth(getContext, getPageWidth())
        }

        return onCreateBaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.apply {
            val data: T = mList[adapterPosition]
            onBindBaseViewHolder(holder, data, adapterPosition)

            setupListener(holder, data, adapterPosition)
            setupWatcher(holder, data, adapterPosition)

            initAPI(holder, data)
            initSecondAdapter(holder, data, adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun getList(): List<T> {
        return mList
    }

    fun setItem(data: T, position: Int, notify: Boolean = true) {
        this.mList[position] = data

        if (notify) {
            notifyItemChanged(position)
        }
    }

    fun replace(content: T) {
        this.mList.clear()
        this.mList.add(content)

        notifyDataSetChanged()
    }

    fun replaceAll(contents: List<T>) {
        this.mList.clear()
        this.mList.addAll(contents)

        notifyDataSetChanged()
    }

    fun add(content: T) {
        this.mList.add(content)
        notifyDataSetChanged()
    }

    fun addAll(contents: List<T>) {
        this.mList.addAll(contents)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        this.mList.removeAt(position)

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun clear() {
        this.mList.clear()
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean {
        return this.mList.isEmpty()
    }

    fun getSize(): Int {
        return this.mList.size
    }

    fun lastIndex(): Int {
        return this.mList.lastIndex
    }

    protected abstract fun onCreateBaseViewHolder(itemView: View): VH
    protected abstract fun onBindBaseViewHolder(holder: VH, data: T, adapterPosition: Int)

    protected open fun initAPI(holder: VH, data: T) {}
    protected open fun setupListener(holder: VH, data: T, adapterPosition: Int) {}
    protected open fun setupWatcher(holder: VH, data: T, adapterPosition: Int) {}
    protected open fun initSecondAdapter(holder: VH, data: T, adapterPosition: Int) {}

    protected open fun layoutResources(resId: Int = 0): Int {
        return 0
    }

    protected open fun getPageWidth(): Float {
        return 1f
    }

    private fun setContext(context: Context) {
        getContext = context
    }

    private fun getResources(viewType: Int): Int {
        return if (layoutResources() == 0) {
            viewType
        } else
            layoutResources()
    }

}