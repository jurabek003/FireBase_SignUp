package uz.turgunboyevjurabek.firebase_signup

import android.content.ContentProviderClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

        if (auth.currentUser!=null){
            startActivity(Intent(this,MainActivity2::class.java))
            finish()
        }
        binding.btnSign.setOnClickListener {
            signIn()
        }
    }
    private fun signIn() {
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account=task.getResult(ApiException::class.java)
                Log.d(TAG,"firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken)
            }catch (e:ApiException){
                Log.d(TAG,"Google sign in failed")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential=GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {task->
                if (task.isSuccessful){
                    Log.d(TAG,"signWithCredential:success")
                    val user=auth.currentUser
                    Toast.makeText(this, "${user?.email}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity2::class.java))
                    finish()
                }else{
                    Log.d(TAG,"signWithCredential:failure",task.exception)
                    Toast.makeText(this,"${task.exception}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}