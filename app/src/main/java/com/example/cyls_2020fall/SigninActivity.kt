package com.example.cyls_2020fall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signin.*

class SigninActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        auth = Firebase.auth

        gotoRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            var account = ""
            var password = ""
            if(accountText.text != null && accountText.text.toString() != ""){
                account = accountText.text.toString()
            }
            if(passwordText.text != null && passwordText.text.toString() != ""){
                password = passwordText.text.toString()
            }
            //********begin of check whether the user is NTU student********
            var email = ""
            if(!account.contains("@")){
                email = account.toLowerCase() + "@ntu.edu.tw"
            }else{
                email = account
            }
            //********end of check whether the user is NTU student********
            println("alskdjaslkdjls")
            account = "r08222011"
            email = "r08222011@ntu.edu.tw"
            password = "b04502131"
            println(account)
            println(password)
            if(account != "" && password != ""){
                //********start of sign in with email********
                println("123123123")
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        println("456456456")
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("LOGIN", "signInWithEmail:success")
                            val user = auth.currentUser
                            println(user)
                            if (user != null) {
                                if(user.isEmailVerified){
                                    println("verified")
                                    println("verified")
                                    println("verified")
                                    println("verified")
                                    println("verified")
                                    startActivity(Intent(this, MainActivity::class.java))
                                }else{
                                    println("unverified")
                                    println("unverified")
                                    println("unverified")
                                    println("unverified")
                                    println("unverified")
                                }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w("LOGIN", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            println("wrong")
                        }

                    }
                //********end of sign in with email********
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        println("current user is :::::::::")
        println(currentUser)
    }
}