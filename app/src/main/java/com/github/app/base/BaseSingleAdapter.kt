package com.github.app.base

import android.content.Context
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.github.app.R
import com.github.ext.inflater.getLayoutInflater

abstract class BaseSingleAdapter<T, VDB : ViewDataBinding>(protected val mList: MutableList<T> = ArrayList()) : RecyclerView.Adapter<BaseViewHolder<VDB>>() {

    companion object {
        const val firstIndex = 0
    }

    @SuppressWarnings("unused")
    lateinit var adapterContext: Context

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    var lastAnimatedPosition = -1

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VDB> {
        return BaseViewHolder(DataBindingUtil.inflate<VDB>(getLayoutInflater(parent), layoutResources(), parent, false).apply {
            setContext(parent.context)
        })
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VDB>, position: Int) {
        if (mList.isEmpty()) return
        holder.apply {
            onBindBaseViewHolder(mViewDataBinding, getData(adapterPosition), adapterPosition)
            if (animated()) setupAnimation(mViewDataBinding, adapterPosition, animResources())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun replaceAll(contents: List<T>) {
        mList.clear()
        mList.addAll(contents)

        notifyDataSetChanged()
    }

    fun add(content: T) {
        mList.add(content)
        notifyDataSetChanged()
    }

    fun add(index: Int, content: T) {
        mList.add(index, content)
        notifyDataSetChanged()
    }

    fun addAll(contents: List<T>) {
        mList.addAll(contents)
        notifyDataSetChanged()
    }

    fun update(position: Int, content: T) {
        mList[position] = content
        notifyItemChanged(position)
    }

    fun remove(position: Int) {
        mList.removeAt(position)

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun clear() {
        mList.clear()
        notifyDataSetChanged()
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun getIndexOf(item: T): Int {
        return mList.indexOf(item)
    }

    private fun getData(position: Int = 0): T {
        return try {
            mList[position]
        } catch (e: Exception) {
            mList.last()
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    fun firstItem() = mList.firstOrNull()
    fun lastItem() = mList.lastOrNull()

    fun lastIndex() = mList.lastIndex
    fun isEmpty() = mList.isEmpty()
    fun isNotEmpty() = mList.isNotEmpty()

    fun getList() = mList
    fun getSize() = mList.size

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun setContext(context: Context) {
        adapterContext = context
    }

    protected open fun getPageWidth(): Float = 1f
    protected open fun getPageHeight(): Float = 1f

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected abstract fun onBindBaseViewHolder(binding: VDB, data: T, adapterPosition: Int)

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    private fun setupAnimation(binding: VDB, adapterPosition: Int, @AnimRes resId: Int) {
        binding.root.run {
            if (adapterPosition > lastAnimatedPosition) {
                animation = AnimationUtils.loadAnimation(adapterContext, resId)
                also { it.animation.start() }
                lastAnimatedPosition = adapterPosition
            } else {
                animation?.cancel()
                clearAnimation()
            }
        }
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected open fun animResources(): Int = R.anim.item_slide_in_bottom
    protected open fun animated(): Boolean = false

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    protected abstract fun layoutResources(): Int

}