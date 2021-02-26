package com.example.multichatkotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.multichatkotlin.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    lateinit var mToolbar: Toolbar
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        mToolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(mToolbar)

        loginButton.setOnClickListener { loginUser() }
        createButton.setOnClickListener { createUser() }

        checkUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // получение item id
        when (item.itemId) {
            R.id.action_sign_out -> {
                Toast.makeText(this,"Sign Out",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkUser(){
        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this, ListOfChatsActivity::class.java)
            intent.putExtra("user", currentUser.email)
            startActivity(intent)

            finish()
        }
    }

    private fun createUser(){
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                checkUser()
            } else {
                task.exception?.let {
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun loginUser(){
        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){
                checkUser()
            } else {
                task.exception?.let {
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}