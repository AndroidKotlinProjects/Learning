package com.littleboysoftware.runaways.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.littleboysoftware.runaways.R
import com.littleboysoftware.runaways.model.getDatabase
import com.littleboysoftware.runaways.repository.MainRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(MainRepository(getDatabase(this).ownerDao, getDatabase(this).dogDao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupInput()

        lifecycleScope.launchWhenStarted {
            viewModel.ownersWithDogs.collect {
                var listOfRelationships: String = ""
                it.forEach {  ownerAndDogs ->
                    listOfRelationships += ownerAndDogs.owner.name
                    ownerAndDogs.dogs.forEach { dog ->
                        listOfRelationships += "    ${dog.name}"
                    }
                    listOfRelationships += "\n\n"
                }
                dogsAndOwners_text.text = listOfRelationships
            }
        }
    }

    private fun setupInput() {
        addOwner_button.setOnClickListener {
            viewModel.addOwner()
        }
        addDog_button.setOnClickListener {
            viewModel.addDog()
        }
    }
}