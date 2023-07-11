package uz.turgunboyevjurabek.firebase_signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import uz.turgunboyevjurabek.firebase_signup.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var googleSingInClient: GoogleSignInClient
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        auth= FirebaseAuth.getInstance()
        val user=auth.currentUser
        binding.tvInfo.text=user!!.displayName

        googleSingInClient = GoogleSignIn.getClient(this, gso)

        binding.thtSignOut.setOnClickListener {
            auth.signOut()
            googleSingInClient.signOut()
                .addOnCompleteListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }

    }
}