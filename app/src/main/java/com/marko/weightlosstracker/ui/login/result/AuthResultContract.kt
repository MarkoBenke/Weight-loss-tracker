package com.marko.weightlosstracker.ui.login.result

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.marko.weightlosstracker.R

class AuthResultContract : ActivityResultContract<Int, String?>() {

    companion object {
        const val INPUT_INT = "input_int"
    }

    override fun createIntent(context: Context, input: Int?): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
        val intent = googleSignInClient.signInIntent
        intent.putExtra(INPUT_INT, input)
        return googleSignInClient.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return when (resultCode) {
            Activity.RESULT_OK -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    account.idToken
                } catch (e: ApiException) {
                    null
                }
            }
            else -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                Log.d("BENI", "parseResult: " + task.exception.toString())
                null
            }
        }
    }
}