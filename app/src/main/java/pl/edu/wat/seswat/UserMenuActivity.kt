package pl.edu.wat.seswat

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.functions.FirebaseFunctions


class UserMenuActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "UserMenuActivity"

    private var mStatusTextView: TextView? = null
    private var mDetailTextView: TextView? = null
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null


    private var mFunctions: FirebaseFunctions? = null
    private var mAuth: FirebaseAuth? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_menu)

//        // Views
//        mStatusTextView = findViewById(R.id.status)
//        mDetailTextView = findViewById(R.id.detail)
//        mEmailField = findViewById(R.id.fieldEmail)
//        mPasswordField = findViewById(R.id.fieldPassword)
//
//        // Buttons
//        findViewById<View>(R.id.emailSignInButton).setOnClickListener(this)
//        findViewById<View>(R.id.emailCreateAccountButton).setOnClickListener(this)
//        findViewById<View>(R.id.signOutButton).setOnClickListener(this)
//        findViewById<View>(R.id.verifyEmailButton).setOnClickListener(this)
//        findViewById<View>(R.id.startAppButton).setOnClickListener(this)


        mAuth = FirebaseAuth.getInstance()
        mFunctions = FirebaseFunctions.getInstance()
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
//        updateUI(currentUser)
    }

//    private fun updateUI(user: FirebaseUser?) {
////        if (user != null) {
////            mStatusTextView?.text = getString(
////                R.string.emailpassword_status_fmt,
////                user.email, user.isEmailVerified
////            )
////            mDetailTextView?.text = getString(R.string.firebase_status_fmt, user.uid)
////
////            findViewById<View>(R.id.emailPasswordButtons).visibility = View.GONE
////            findViewById<View>(R.id.emailPasswordFields).visibility = View.GONE
////            findViewById<View>(R.id.startAppButton).visibility = View.VISIBLE
////            findViewById<View>(R.id.signedInButtons).visibility = View.VISIBLE
////            findViewById<View>(R.id.verifyEmailButton).isEnabled = !user.isEmailVerified
////            findViewById<View>(R.id.startAppButton).isEnabled = user.isEmailVerified
////
////
////        } else {
////            mStatusTextView?.setText(R.string.signed_out)
////            mDetailTextView?.text = null
////
////            findViewById<View>(R.id.emailPasswordButtons).visibility = View.VISIBLE
////            findViewById<View>(R.id.emailPasswordFields).visibility = View.VISIBLE
////            findViewById<View>(R.id.startAppButton).visibility = View.GONE
////            findViewById<View>(R.id.signedInButtons).visibility = View.GONE
////            findViewById<View>(R.id.startAppButton).isEnabled = false
////        }
//    }

    override fun onClick(v: View) {
//        val i = v.id
//        if (i == R.id.emailCreateAccountButton) {
//            createAccount(mEmailField?.text.toString(), mPasswordField?.text.toString())
//        } else if (i == R.id.emailSignInButton) {
//            signIn(mEmailField?.text.toString(), mPasswordField?.text.toString())
//        } else if (i == R.id.signOutButton) {
//            signOut()
//        } else if (i == R.id.verifyEmailButton) {
//            sendEmailVerification()
//        } else if (i == R.id.startAppButton) {
////            val myIntent = Intent(this, MenuActivity::class.java)
////            startActivity(myIntent)
//        }
    }

}