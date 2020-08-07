package com.littleboysoftware.roomdeveloperguide.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.littleboysoftware.roomdeveloperguide.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels { MainViewModelFactory(getApplication()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBindings()
        lifecycleScope.launchWhenStarted {
            doSubscriptions()
        }
    }

    private suspend fun doSubscriptions() {
        viewModel.userStateFlow.collect {
            var allUsers: String = ""
            it.forEach {
                allUsers += it.generateProfile() + "\n"
            }
            user_info_text.text = allUsers
        }
    }

    private fun setBindings() {
        insert_random_button.setOnClickListener {
            viewModel.pushUser()
        }
        fetch_all_button.setOnClickListener {
            viewModel.pullUsers()
        }
        delete_all_button.setOnClickListener {
            viewModel.deleteUsers()
        }
    }

}