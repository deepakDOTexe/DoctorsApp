package ai.kitt.snowboy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ai.kitt.snowboy.demo.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById((R.id.password));
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
////        FirebaseUser currentUser = mAuth.getCurrentUser();
////
////        if(currentUser==null){
////            Toast.makeText(this,"Please sign in",Toast.LENGTH_LONG).show();
////        }
////        else {
////            Toast.makeText(this, "You are signed", Toast.LENGTH_LONG).show();
////        }
//    }

    public void signInCheck(View view){
        Intent i =new Intent(getApplicationContext(),DashboardActivity.class);
        startActivity(i);

//        Log.i("mytag","working !!");
////        Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
//
//        String e,p;
//        if(email.getText().toString().equals("") || password.getText().toString().equals(""))
//        {
//            e=".";
//            p=".";
//        }
//        else
//        {
//            e=email.getText().toString();
//            p=password.getText().toString();
//        }
//
//
//        mAuth.signInWithEmailAndPassword(e,p)
//            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.i("signedin", "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//
//                        Intent i =new Intent(getApplicationContext(),DashboardActivity.class);
//                        startActivity(i);
//
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.i("failed", "signInWithEmail:failure", task.getException());
//                        Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
//                    }
//
//                    // ...
//                }
//            });
    }
}
