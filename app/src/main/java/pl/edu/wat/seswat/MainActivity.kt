package pl.edu.wat.seswat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.util.Log
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.functions.FirebaseFunctions


class MainActivity : AppCompatActivity(), View.OnClickListener  {

    val TAG = "LoginActivity"

    private var mStatusTextView: TextView? = null
    private var mDetailTextView: TextView? = null
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null


    private var mFunctions: FirebaseFunctions? = null
    private var mAuth: FirebaseAuth? = null


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Views
        mStatusTextView = findViewById(R.id.status)
        mDetailTextView = findViewById(R.id.detail)
        mEmailField = findViewById(R.id.fieldEmail)
        mPasswordField = findViewById(R.id.fieldPassword)

        // Buttons
        findViewById<View>(R.id.emailSignInButton).setOnClickListener(this)
        findViewById<View>(R.id.emailCreateAccountButton).setOnClickListener(this)
        findViewById<View>(R.id.signOutButton).setOnClickListener(this)
        findViewById<View>(R.id.verifyEmailButton).setOnClickListener(this)
        findViewById<View>(R.id.startAppButton).setOnClickListener(this)


        mAuth = FirebaseAuth.getInstance()
        mFunctions = FirebaseFunctions.getInstance()
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun createAccount(email: String, password: String) {
        Log.d(TAG, "createAccount:$email")
        if (!validateForm()) {
            return
        }


        // [START create_user_with_email]
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    addUserToFirebase()
                    val user = mAuth!!.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(
                        TAG,
                        "createUserWithEmail:failure",
                        task.exception
                    )
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
        // [END create_user_with_email]
    }

    private fun addUserToListFunction(): Task<String>? {
        // Create the arguments to the callable function.

        return mFunctions
            ?.getHttpsCallable("addUserToListFunction")
            ?.call()
            ?.continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then getResult() will throw an Exception which will be
                // propagated down.
                task.result?.data.toString()
            }
    }

    //TODO
    fun addUserToFirebase() {
        return
//        addUserToListFunction()!!.addOnCompleteListener(this
//        ) { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "addUserToListFunction:success")
//                val user = mAuth?.currentUser
//                updateUI(user)
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w(
//                    TAG,
//                    "addUserToListFunction:failure",
//                    task.exception
//                )
//                updateUI(null)
//            }
//        }
    }

    private fun signIn(email: String, password: String) {
        Log.d(TAG, "signIn:$email")
        if (!validateForm()) {
            return
        }

        // [START sign_in_with_email]
        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth?.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@MainActivity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // [START_EXCLUDE]
                if (!task.isSuccessful) {
                    mStatusTextView?.setText(R.string.auth_failed)
                }
                // [END_EXCLUDE]
            }
        // [END sign_in_with_email]
    }

    private fun signOut() {
        mAuth?.signOut()
        updateUI(null)
    }

    private fun sendEmailVerification() {
        // Disable button
        findViewById<View>(R.id.verifyEmailButton).isEnabled = false

        // Send verification email
        // [START send_email_verification]
        val user = mAuth?.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this
            ) { task ->
                // [START_EXCLUDE]
                // Re-enable button
                findViewById<View>(R.id.verifyEmailButton).isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(
                        this@MainActivity,
                        "Verification email sent to " + user.email,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // [END_EXCLUDE]
            }
        // [END send_email_verification]
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = mEmailField?.text.toString()
        if (TextUtils.isEmpty(email)) {
            mEmailField?.error = "Required."
            valid = false
        } else {
            mEmailField?.error = null
        }

        val password = mPasswordField?.text.toString()
        if (TextUtils.isEmpty(password)) {
            mPasswordField?.error = "Required."
            valid = false
        } else {
            mPasswordField?.error = null
        }

        val domain = email.substring(email.indexOf("@") + 1)
        if (domain != "student.wat.edu.pl" && domain != "wat.edu.pl") {
            Toast.makeText(
                this,
                "Email musi być z domeny student.wat.edu.pl lub wat.edu.pl",
                Toast.LENGTH_SHORT
            ).show()
            mEmailField?.error = "Required from: student.wat.edu.pl or wat.edu.pl ."
            valid = false
        }

        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            mStatusTextView?.text = getString(
                R.string.emailpassword_status_fmt,
                user.email, user.isEmailVerified
            )
            mDetailTextView?.text = getString(R.string.firebase_status_fmt, user.uid)

            findViewById<View>(R.id.emailPasswordButtons).visibility = View.GONE
            findViewById<View>(R.id.emailPasswordFields).visibility = View.GONE
            findViewById<View>(R.id.startAppButton).visibility = View.VISIBLE
            findViewById<View>(R.id.signedInButtons).visibility = View.VISIBLE
            findViewById<View>(R.id.verifyEmailButton).isEnabled = !user.isEmailVerified
            findViewById<View>(R.id.startAppButton).isEnabled = user.isEmailVerified


        } else {
            mStatusTextView?.setText(R.string.signed_out)
            mDetailTextView?.text = null

            findViewById<View>(R.id.emailPasswordButtons).visibility = View.VISIBLE
            findViewById<View>(R.id.emailPasswordFields).visibility = View.VISIBLE
            findViewById<View>(R.id.startAppButton).visibility = View.GONE
            findViewById<View>(R.id.signedInButtons).visibility = View.GONE
            findViewById<View>(R.id.startAppButton).isEnabled = false
        }
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField?.text.toString(), mPasswordField?.text.toString())
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField?.text.toString(), mPasswordField?.text.toString())
        } else if (i == R.id.signOutButton) {
            signOut()
        } else if (i == R.id.verifyEmailButton) {
            sendEmailVerification()
        } else if (i == R.id.startAppButton) {
            val myIntent = Intent(this, MainMenuActivity::class.java)
            startActivity(myIntent)
        }
    }
}