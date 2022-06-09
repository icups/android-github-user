package com.github.app.ui.user

import com.github.app.R
import com.github.app.base.BaseSingleAdapter
import com.github.app.databinding.ItemUserBinding
import com.github.app.ui.main.MainViewModel
import com.github.network.model.User

class UserAdapter(private val viewModel: MainViewModel) : BaseSingleAdapter<User, ItemUserBinding>() {

    override fun onBindViewHolder(binding: ItemUserBinding, data: User, adapterPosition: Int) {
        binding.run {
            vm = viewModel
            item = data
        }
    }

    override fun layoutResources(): Int {
        return R.layout.item_user
    }

    override fun animated(): Boolean {
        return true
    }

}