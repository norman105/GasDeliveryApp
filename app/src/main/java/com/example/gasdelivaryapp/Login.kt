package com.example.gasdelivaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //variables to store user input
    var emailLogin: String= ""
    var passwordLogin: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
            auth= FirebaseAuth.getInstance()

        blogin.setOnClickListener {
            captureInput()

        }

        textView.setOnClickListener {
            val intent = Intent(applicationContext,ForgotPassword::class.java)
            startActivity(intent)

            creataccount.setOnClickListener {
                val intent = Intent(applicationContext,CreateAccount::class.java)
                startActivity(intent)

            }
    }
}

    private fun captureInput() {
        emailLogin =emailL.text.toString()
        passwordLogin =ppasswordL.text.toString()
        //validation of inputs
        if (emailLogin.isEmpty() && passwordLogin.isEmpty()) {
            Toast.makeText(applicationContext, "Feild cannot be empty", Toast.LENGTH_LONG).show()
        }else{
            loginUser(emailLogin,passwordLogin)
        }
    }

    private fun loginUser(emailLogin: String, passwordLogin: String) {
        auth.signInWithEmailAndPassword(emailLogin,passwordLogin)
            .addOnCompleteListener {
                if (it.isComplete) {
                    Toast.makeText(applicationContext, "Login verifired", Toast.LENGTH_LONG).show()
                    updateUi()


                }else{
                    Toast.makeText(applicationContext, "Login Failure", Toast.LENGTH_LONG).show()
                    Log.d("auth","details are" + it.exception)
                }
            }

    }

    private fun updateUi() {
        val intent = Intent(applicationContext,Home::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        val  currentUser= auth.currentUser
        if (currentUser != null){
            updateUi()
    }
    }
}


