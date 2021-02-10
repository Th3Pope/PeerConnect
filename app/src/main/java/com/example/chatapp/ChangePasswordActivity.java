package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText oldPassword, newPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
    }

    public void btnChangePassword(View view)
    {
        String password = oldPassword.getText().toString().trim();
        String confirmPassword = newPassword.getText().toString().trim();

        if(password.equals(""))
        {
            oldPassword.setError(getString(R.string.change_password));
        } else if (confirmPassword.equals(""))
        {
            newPassword.setError(getString(R.string.confirm_password));
        }
        else if(!password.equals(confirmPassword))
        {
            newPassword.setError(getString(R.string.password_mismatch));
        }else
        {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if(firebaseUser !=null)
            {
                firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ChangePasswordActivity.this,
                                    R.string.password_change_successfully,
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ChangePasswordActivity.this, getString(R.string.something_went_wrong, task.getException()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }
}