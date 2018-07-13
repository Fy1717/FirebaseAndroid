package com.example.x.teamlist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    Button Add,Show,Exit;
    TextView kayıtlar;
    EditText name,surname,email,phone;



    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef =database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Add =(Button)findViewById(R.id.buttonAdd);
        Show =(Button)findViewById(R.id.buttonShow);
        Exit=(Button) findViewById(R.id.buttonExit);
        kayıtlar=(TextView)findViewById(R.id.kayıtlar);
        name=(EditText)findViewById(R.id.editName);
        surname=(EditText)findViewById(R.id.editSurname);
        email=(EditText)findViewById(R.id.editMail);
        phone=(EditText)findViewById(R.id.editPhone);

        final Context context = this;


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String getName, getSurname,getMail,getPhone;

                getName = name.getText().toString();
                getSurname = surname.getText().toString();
                getMail = email.getText().toString();
                getPhone = phone.getText().toString();

                AddNewUser(getName,getSurname,getMail,getPhone);
            }
        });




        final StringBuffer buffer = new StringBuffer();
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbGelenler= database.getReference("USERS");
                dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot gelenler : dataSnapshot.getChildren()){
                            buffer.append(gelenler.getValue(User.class).getName()+"  "+gelenler.getValue(User.class).getSurname()+"\n"+gelenler.getValue(User.class).getEmail()+"\n"+gelenler.getValue(User.class).getPhone()+"\n\n");
                        }
                        showMessage("Kayıtlar",buffer.toString());
                        buffer.delete(0,999999999);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });




        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Bir Alert Dialog Örneği");
                alertDialogBuilder
                        .setMessage("Çıkmak istiyor musunuz?")
                        .setCancelable(false)
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }


    private void AddNewUser(String name,String surname,String mail,String phone){

        User kullanıcı= new User(name,surname,mail,phone);
        String key= myRef.push().getKey();
        myRef.child("USERS/"+key).setValue(kullanıcı);
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
