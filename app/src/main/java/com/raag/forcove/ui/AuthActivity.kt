package com.raag.forcove.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import com.pdfview.PDFView
import com.raag.forcove.R
import com.raag.forcove.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    companion object {
        private const val RC_SIGN_IN = 123
    }
    private lateinit var googleSignInClient: GoogleSignInClient
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setAnimation(R.raw.google)
        binding.btnLogin.playAnimation()

        val user = mAuth.currentUser
        if(user != null ){
            finish()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        linkPolitic()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.deAcuerdo) {
                binding.btnLogin.isClickable = true
                binding.btnLogin.setAnimation(R.raw.login)
                binding.btnLogin.playAnimation()
                binding.btnLogin.loop(false)
                binding.resgistro.visibility = View.VISIBLE

                binding.btnLogin.setOnClickListener {
                    signIn()
                    binding.deAcuerdo.isChecked = false
                }
            }

            if (checkedId == R.id.desAcuerdo) {
                binding.btnLogin.isClickable = false
                binding.btnLogin.setAnimation(R.raw.google)
                binding.btnLogin.playAnimation()
                binding.btnLogin.loop(true)
                binding.resgistro.visibility = View.INVISIBLE
                Snackbar.make(binding.root, getString(R.string.debes_registro), Snackbar.LENGTH_SHORT).show()
            }

        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun linkPolitic() {
        val politic = Link("Ver política")
            .setTextColor(Color.RED)
            .setTextColorOfHighlightedLink(Color.WHITE)
            .setHighlightAlpha(4f)
            .setUnderlined(true)
            .setBold(false)
            .setOnClickListener {
                val politica = AlertDialog.Builder(this)
                val politicView = layoutInflater.inflate(R.layout.politicapdf, null)
                politica.setView(politicView)
                val pdf = politicView.findViewById<PDFView>(R.id.pdfView)
                val close = politicView.findViewById<TextView>(R.id.cerrarPolitica)
                val dialog = politica.create()
                dialog.show()
                dialog.setCanceledOnTouchOutside(false)

                close.setOnClickListener {
                    dialog.dismiss()
                }

                pdf.fromAsset("politica.pdf").show()
            }
        binding.contrato2.applyLinks(politic)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("SignInActiviy", "signInWithCredential:success")
                val i = Intent(this, HomeActivity::class.java)
                startActivity(i)
            } else {
                Snackbar.make(binding.root, getString(R.string.eroor_google), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActiviy", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Snackbar.make(binding.root, "Error al autenticar: ${e.statusCode}", Snackbar.LENGTH_SHORT).show()
                }
            } else{
                Snackbar.make(binding.root, "Error de autenticación: ${exception.hashCode()}", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}