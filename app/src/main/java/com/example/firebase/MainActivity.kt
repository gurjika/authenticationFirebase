package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        registerButton.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString().trim()
            val password = editTextTextPassword.text.toString()
            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "შეიყვანეთ მონაცემები", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "წარმატებით შეიქმნა!", Toast.LENGTH_SHORT).show()

                    } else {
                        when (task.exception) {
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    this,
                                    "მომხმარებელი უკვე არსებობს",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(
                                    this,
                                    "პაროლი უნდა შეიცავდეს 6 ან მეტ სიმბოლოს",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is FirebaseNetworkException -> {
                                Toast.makeText(this,
                                    "არ არის კავშირი ინტერნეტთან",
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }

                            else -> {
                                Toast.makeText(
                                    this,
                                    "არასწორი იმეილი ან პაროლი",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        }
    }
}