package com.example.android.damasimultanea;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class AuthenticationHandler {

    private final int RC_SIGN_IN = 1;
    private final String ANONYMUS = "anonymus";
    final private Context context;
    private String mUsername;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public AuthenticationHandler(final Context context_in, FirebaseAuth mFirebaseAuth_in) {
        context = context_in;
        mFirebaseAuth = mFirebaseAuth_in;
        mAuthStateListener = new CreateFirebaseAuthListener();
    }

    public void addListener(){
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void removeListener() {
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    public void logOut(){
        AuthUI.getInstance().signOut(context);
    }

    public String getUserName(){
        return mUsername;
    }

    public int getAuthenticationRequestedCode(){
        return RC_SIGN_IN;
    }

    private class CreateFirebaseAuthListener implements FirebaseAuth.AuthStateListener {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Toast.makeText(
                        context,
                        "Signer in, Welcome",
                        Toast.LENGTH_SHORT).show();

            } else {
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build());
                ((Activity) context).startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
        }
    }

}
