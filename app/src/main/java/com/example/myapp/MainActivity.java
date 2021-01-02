package com.example.myapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger.log("22222222222222", "33333333333333333");
        setContentView(R.layout.activity_main);
        Logger.log("22222222222222", "4444444444444444444444444444");


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_FULL))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_place, new First_fragment()).commit();

        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            actionBar.setTitle(bundle.getString("email"));
        }



    }
    public void selectFrag(View view){
        Fragment fr;
        if(view == findViewById(R.id.btn1)){
            fr = new First_fragment();
        }else if (view == findViewById(R.id.btn2)){
            fr = new Second_fragment();
        }else if (view == findViewById(R.id.btn3)){

            fr = new Third_fragment();
        }else{
            fr = new Fourth_fragment();
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();
    }




    //toolbar UI
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action, menu);
        return true;
    }


    //signout when LOGOUT btn is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LOGOUT:
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                finish();
                            }
                        });
        }
        return super.onOptionsItemSelected(item);
    }
}