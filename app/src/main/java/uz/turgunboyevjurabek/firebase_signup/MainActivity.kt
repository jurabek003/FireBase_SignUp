package uz.turgunboyevjurabek.firebase_signup

import android.content.ContentProviderClient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import uz.turgunboyevjurabek.firebase_signup.databinding.ActivityMainBinding
private const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient= GoogleSignIn.getClient(this,gso)
        auth= FirebaseAuth.getInstance()

        binding.btnSign.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent,1)
    }
}