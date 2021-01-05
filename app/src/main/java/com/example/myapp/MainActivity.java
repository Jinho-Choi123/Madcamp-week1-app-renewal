package com.example.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
    Button btn1, btn2, btn3, btn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_FULL))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        String googleUserId = acct.getId();
        Context context = this;

        SharedPreferences sf = getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userId = sf.getString("userId", "");
        DB db = new DB(userId);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            actionBar.setTitle("Hello, " + bundle.getString("given_name")+" "+ bundle.getString("family_name"));

        }

        btn1 = findViewById(R.id.selectFrag1);
        btn2 = findViewById(R.id.selectFrag2);
        btn3 = findViewById(R.id.selectFrag3);
        btn4 = findViewById(R.id.selectFrag4);
        btn1.setBackgroundColor(Color.GRAY);
        btn2.setBackgroundColor(Color.BLACK);
        btn3.setBackgroundColor(Color.BLACK);
        btn4.setBackgroundColor(Color.BLACK);
    }


    public void loadFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }

    public void selectFrag(View view) {
        Fragment fr;


        if(view == findViewById(R.id.selectFrag1)) {
            fr = new First_fragment();
            btn1.setBackgroundColor(Color.GRAY);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
        }
        else if(view == findViewById(R.id.selectFrag2)) {
            fr = new Second_fragment();
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.GRAY);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.BLACK);
        }
        else if(view == findViewById(R.id.selectFrag3)) {
            fr = new Third_fragment();
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.GRAY);
            btn4.setBackgroundColor(Color.BLACK);
        }
        else {
            fr = new Fourth_fragment();
            btn1.setBackgroundColor(Color.BLACK);
            btn2.setBackgroundColor(Color.BLACK);
            btn3.setBackgroundColor(Color.BLACK);
            btn4.setBackgroundColor(Color.GRAY);
        }

        loadFragment(fr);
        return;
    }

    //toolbar UI
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action, menu);
        return true;
    }


    //signout when LOGOUT btn is clicked
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sf = getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userId = sf.getString("userId", "");
        DB db = new DB(userId);
        switch (item.getItemId()) {
            case R.id.LOGOUT:
                SharedPreferences.Editor editor = sf.edit();
                editor.putString("userId", "0");
                editor.apply();
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
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}