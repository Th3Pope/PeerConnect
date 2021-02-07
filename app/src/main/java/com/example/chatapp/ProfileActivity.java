package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    private EditText emailXML, nameXML, usernameXML;
    private String email, name, username;
    private TextView textToSignInXML;
    private FirebaseAuth mFirebaseAuth;
    private ImageView ivProfile;

    private StorageReference fileStorage;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private Uri localFileUri, serverFileUri;
    TabLayout tabLayout;

    //so user can logout
    public void btnLogoutclick(View view)
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
        //finish---- cant click back arrow to go back to profile page
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Remove the title bar
        if(this.getSupportActionBar() != null)
        {
            this.getSupportActionBar().hide();
        }

        tabLayout = (TabLayout) findViewById(R.id.tabBar);

        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.addTab(tabLayout.newTab().setText("Alerts"));
        tabLayout.addTab(tabLayout.newTab().setText("Find"));
        tabLayout.addTab(tabLayout.newTab().setText("About"));

        //makes chat that good purp
        tabLayout.getTabAt(4).select();

        //TODO:Set up remove it when done
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPos = tabLayout.getSelectedTabPosition();
                switch (tabPos) {
                    case 0: {

                        startActivity(new Intent(ProfileActivity.this, MapsActivity.class));
                    }
                    case 1: {
                         startActivity(new Intent(ProfileActivity.this, ChatActivity.class));
                    }
                    case 2: {
                    }
                    case 3: {
                    }
                    case 4: {
                        //startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));}
                    }

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        emailXML = findViewById(R.id.emailSignUp);
        nameXML = findViewById(R.id.legalNameSignUp);
        usernameXML = findViewById(R.id.userNameSignUp);
        ivProfile = findViewById(R.id.ivProfile);

        fileStorage = FirebaseStorage.getInstance().getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            //display users info
            nameXML.setText(firebaseUser.getDisplayName());
            emailXML.setText(firebaseUser.getDisplayName());
            usernameXML.setText(firebaseUser.getDisplayName());
            serverFileUri = firebaseUser.getPhotoUrl();

            //use glide library to show users image, if no picture is select or error happen, defualt picture will show
            if(serverFileUri!=null)
            {
                Glide.with(this)
                        .load(serverFileUri)
                        .placeholder(R.drawable.default_picture)
                        .error(R.drawable.default_picture)
                        .into(ivProfile);
            }
        }
    }



}