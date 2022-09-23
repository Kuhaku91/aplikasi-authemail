package com.laudry.authemail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var auth : FirebaseAuth
    var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnRegister.setOnClickListener(this)
        auth = Firebase.auth
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnRegister -> {
                auth.createUserWithEmailAndPassword(edRegEmail.text.toString(), edRegPassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            currentUser = auth.currentUser
                            if (currentUser!=null){
                                currentUser!!.updateProfile(
                                    userProfileChangeRequest {
                                        displayName = edRegUsername.text.toString()
                                    }
                                )
                                currentUser!!.sendEmailVerification()
                            }
                            Toast.makeText(this, "Berhasil mendaftarkan user, silahkan cek email", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
            }
        }
    }
}