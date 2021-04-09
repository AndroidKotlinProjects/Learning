package com.littleboysoftware.firebasesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private val personCollectionReference = Firebase.firestore.collection("people")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit_button.setOnClickListener {
            if (firstName_editText.text.isEmpty()
                || lastName_editText.text.isEmpty()
                || age_editText.text.isEmpty()) {
                Toast.makeText(this, "Please fill-in all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val person = Person(
                firstName_editText.text.toString(),
                lastName_editText.text.toString(),
                age_editText.text.toString().toInt()
            )
            savePerson(person)
            firstName_editText.text.clear()
            lastName_editText.text.clear()
            age_editText.text.clear()
        }

        pull_button.setOnClickListener {
            retrievePeople()
        }
    }

    private suspend fun handleException(e: Exception) {
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun savePerson(person: Person) = CoroutineScope(Dispatchers.IO).launch {
        try {
            personCollectionReference.add(person).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Successfully added: $person", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun retrievePeople() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = personCollectionReference.get().await()
            val string = StringBuilder()
            querySnapshot.documents.forEach { document ->
                string.append("${document.toObject<Person>()}\n")
            }
            withContext(Dispatchers.Main) {
                people_text.text = string
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
}