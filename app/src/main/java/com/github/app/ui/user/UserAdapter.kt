package com.github.app.ui.user

import com.github.app.R
import com.github.app.base.BaseSingleAdapter
import com.github.app.databinding.ItemUserBinding
import com.github.model.User

class UserAdapter : BaseSingleAdapter<User, ItemUserBinding>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return try {
            mList[position].id.toLong()
        } catch (ex: Exception) {
            0L
        }
    }

    override fun onBindBaseViewHolder(binding: ItemUserBinding, data: User, adapterPosition: Int) {
        binding.item = data
    }

    override fun layoutResources(): Int {
        return R.layout.item_user
    }

    override fun animated(): Boolean {
        return true
    }

}