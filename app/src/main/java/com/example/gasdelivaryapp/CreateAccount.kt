package com.example.gasdelivaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_forgot_password.*

class CreateAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var email: String= ""
    var password: String=""
    var conFirpass:String=""
    var userNAME : String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        auth = FirebaseAuth.getInstance()

        loginc.setOnClickListener {
            val intent = Intent(applicationContext,Home::class.java)
            startActivity(intent)
        }
        forgotpasswordc.setOnClickListener {
            val intent = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener {
            captureInput()
        }

    }

    private fun captureInput() {
        email = emailc.text.toString()

        password = passwordc.text.toString()
        conFirpass = confirmpassword.text.toString()
        userNAME = usernamec.text.toString()

        if (!password.equals(conFirpass) && email.isEmpty() && password.isEmpty()) {
            Toast.makeText(
                    applicationContext,
                    "Pasword do not match or field cannot be empty",
                    Toast.LENGTH_SHORT
            ).show()
        }else {
            registerToFirebase(email, password)
        }

    }

    private fun registerToFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(applicationContext, "Account Created", Toast.LENGTH_SHORT).show()
                        Log.d("auth","details are " + it.exception)
                        updateUi()

                    }else{
                        Toast.makeText(applicationContext, "Account not created try again", Toast.LENGTH_SHORT).show()
                        Log.d("auth","details are " + it.exception)

                    }

                }

                }

    private fun updateUi() {
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)

    }

}
