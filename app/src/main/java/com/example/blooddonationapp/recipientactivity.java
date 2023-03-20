package com.example.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class recipientactivity extends AppCompatActivity {

    private TextView backbutton;


    private CircleImageView profile_image;

    private TextInputEditText registername,phonenumber,emailid,enteraddress ,enterpassword, uniqueid;

    private Spinner bloodgroup;

    private Button registerbutton;

    private Uri profileuri;

    private ProgressDialog registeringuser;

    private FirebaseAuth forauthentication;

    private DatabaseReference userdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepientactivity);

        backbutton =findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(recipientactivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        profile_image =findViewById(R.id.profile_image1);
        registername =findViewById(R.id.registername);
        phonenumber =findViewById(R.id.phonenumber);
        emailid=findViewById(R.id.emailid);
        enteraddress=findViewById(R.id.Address);
        enterpassword=findViewById(R.id.enterpassword);
        bloodgroup=findViewById(R.id.bloodgroup);
        registerbutton= findViewById(R.id.registerbutton);
        uniqueid = findViewById(R.id.uniqueid1);


        registeringuser = new ProgressDialog(this);

        forauthentication = FirebaseAuth.getInstance();

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String email  = emailid.getText().toString().trim();
                final String Password = enterpassword.getText().toString().trim();
                final String fullname = registername.getText().toString().trim();
                final String address = enteraddress.getText().toString().trim();
                final  String Phone = phonenumber.getText().toString().trim();
                final String blood = bloodgroup.getSelectedItem().toString();
                final String uniquerecipient = uniqueid.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    emailid.setError("Email Required Here ! ");
                    return;
                }
                if (TextUtils.isEmpty(uniquerecipient))
                {
                    uniqueid.setError("Id Requried ! ");
                    return;
                }
                if (TextUtils.isEmpty(Password))
                {
                    enterpassword.setError("Enter Password ! ");
                    return;
                }
                if (TextUtils.isEmpty(fullname))
                {
                    registername.setError("Name is Requried ! ");
                    return;
                }
                if (TextUtils.isEmpty(address))
                {
                    enteraddress.setError("Enter Address ! ");
                    return;
                }
                if (TextUtils.isEmpty(Phone))
                {
                    phonenumber.setError("Number Requried ! ");
                    return;
                }
                if (bloodgroup.equals("Select your blood group"))
                {
                    Toast.makeText(recipientactivity.this, "Select blood group ", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    registeringuser.setMessage("Registering Wait !");
                    registeringuser.setCanceledOnTouchOutside(false);
                    registeringuser.show();

                    forauthentication.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                String error = task.getException().toString();
                                Toast.makeText(recipientactivity.this, "Error"+error, Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String Currentuserid = forauthentication.getCurrentUser().getUid();
                                userdatabase = FirebaseDatabase.getInstance().getReference().child("User details").child(Currentuserid);

                                HashMap userinformation = new HashMap();
                                userinformation.put("id",Currentuserid);
                                userinformation.put("name",fullname);
                                userinformation.put("phonenumber",Phone);
                                userinformation.put("address",address);
                                userinformation.put("UniqueId",uniquerecipient);
                                userinformation.put("emailid",email);
                                userinformation.put("bloodgroup",blood);
                                userinformation.put("type","recipient");
                                userinformation.put("search","recipient"+blood);

                                userdatabase.updateChildren(userinformation).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(recipientactivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(recipientactivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
//                                         registeringuser.dismiss();

                                    }
                                });

                                if (profileuri!=null)
                                {
                                    final StorageReference pathoffile = FirebaseStorage.getInstance().getReference().child("profileimage").child(Currentuserid);
                                    Bitmap bitmap = null;
                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),profileuri);


                                    }catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }

                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20,byteArrayOutputStream);
                                    byte[] data = byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask = pathoffile.putBytes(data);

                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(recipientactivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if (taskSnapshot.getMetadata()!= null && taskSnapshot.getMetadata().getReference()!= null)

                                            {
                                                Task<Uri> image = taskSnapshot.getStorage().getDownloadUrl();
                                                image.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl = uri.toString();
                                                        Map newimagemap = new HashMap();
                                                        newimagemap.put("profilepictureurl",imageUrl);

                                                        userdatabase.updateChildren(newimagemap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful())
                                                                {
                                                                    Toast.makeText(recipientactivity.this, "Image url Successfull stored to database", Toast.LENGTH_SHORT).show();
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(recipientactivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });

                                                        finish();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    Intent intent = new Intent(recipientactivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    registeringuser.dismiss();
                                }

                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            profileuri = data.getData();
            profile_image.setImageURI(profileuri);
        }
    }
}