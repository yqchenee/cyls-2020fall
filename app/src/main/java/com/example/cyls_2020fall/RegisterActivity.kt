package com.example.cyls_2020fall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //firebase auth
        auth = Firebase.auth

        //make textview scrollable
        registerWarningarningView.movementMethod = ScrollingMovementMethod()

        registerButton.setOnClickListener{
            // only allow NTU student
            var account = ""
            var password = ""
            if(accountField.text != null && accountField.text.toString() != ""){
                account = accountField.text.toString().toLowerCase()
            }
            if(passwordField.text != null && passwordField.text.toString() != ""){
                password = passwordField.text.toString()
            }
            val email = "$account@ntu.edu.tw"

            //*********begin of check account and password********
            if(account == "" || password == ""){
                println("niilinlinlnlinlin")
            }else if(account.length != 9 || account.contains("@")){
                showAlert("Error", "Seems like you are not a NTU student.")
            }else if(passwordContainSpecialChar(password)){
                showAlert("Special Character detected", "password should not contain " +
                        "[, `, ~, !, @, #, \$, %, ^, &, *, (, ), _, -, +, =, {, }, [, ], \\, |, :, ;, <, ,, >, ., ?, /, \", ', ]")
            }else if(password.length < 8 || password.length > 20){
                showAlert("Wrong password length", "Password should be >=8 or <=20")
            }else if(!warningCheckBox.isChecked){
                showAlert("Checkbox unchecked", "Please read the instruction.")
                //*********end of check account and password********
            }else{

                // ************ start adding account ***********
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d("REGISTER", "createUserWithEmail:success")
                            val user = auth.currentUser
                            startActivity(Intent(this, SigninActivity::class.java))

                            println("success")

                            user!!.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        //Log.d("SEND VERIFICATION", "Email sent.")
                                        println("send")
                                    }
                                }
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w("REGISTER", "createUserWithEmail:failure", task.exception)
                            println("fail")
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            // ************ start adding account ***********
        }

    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        println("updateUI")
    }

    private fun passwordContainSpecialChar(password: String): Boolean{
        val special = "` ~ ! @ # $ % ^ & * ( ) _ - + = { } [ ] | : ; < , > . ? / '".split(" ")
        for (element in special){
            if(password.contains(element)){
                return true
            }
        }
        return false
    }

    private fun showAlert(title: String, message: String){
        println(title)
        println(message)
    }
}