package com.littleboysoftware.stateflowexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/* from: https://medium.com/scalereal/stateflow-end-of-livedata-a473094229b3 */

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObservers()
        initView()
    }

    fun initObservers() {
        lifecycleScope.launch {
            viewModel.countState.collect { value ->
                counter_text.text = value.toString()
            }
        }
    }

    fun initView() {
        increment_button.setOnClickListener { viewModel.incrementCount() }
        decrement_button.setOnClickListener { viewModel.decrementCount() }
    }
}