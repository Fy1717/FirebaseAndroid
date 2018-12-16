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


    Button Add, Show, Exit;
    TextView mesajlar;
    EditText name, mesaj;


    FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Add = (Button) findViewById(R.id.buttonAdd);
        Show = (Button) findViewById(R.id.buttonShow);
        Exit = (Button) findViewById(R.id.buttonExit);
        mesajlar = (TextView) findViewById(R.id.mesajlar);
        name = (EditText) findViewById(R.id.userName);
        mesaj = (EditText) findViewById(R.id.userMessage);

        final Context context = this;


        DatabaseReference dbGelenler = db.getReference("MESSAGES");
        dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot key : dataSnapshot.getChildren()) {
                    mesajlar.append("Kullanıcı : "+key.getValue(User.class).getName() +"\n");
                    mesajlar.append("Mesaj     :    "+key.getValue(User.class).getMesaj() + "\n");
                    mesajlar.append("------------------------------------------------------------------------------------------------------------------------------" + "\n");


                    // buffer.append(gelenler.getValue(User.class).getName()+"\n   "+gelenler.getValue(User.class).getMesaj()+"\n\n");
                }
                //showMessage("Mesajlar",buffer.toString());
            }


        // Ekleme Metodumuz
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String getName, getMessage;

                getName = name.getText().toString();
                getMessage = mesaj.getText().toString();
                System.out.println(getName);
                AddNewUser(getName, getMessage);
            }
        });


        //final StringBuffer buffer = new StringBuffer();
        // Okuma Metodumuz
        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference dbGelenler = db.getReference("MESSAGES");
                dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot key : dataSnapshot.getChildren()) {
                            mesajlar.append("Kullanıcı : "+key.getValue(User.class).getName() +"\n");
                            mesajlar.append("Mesaj     :    "+key.getValue(User.class).getMesaj() + "\n");
                            mesajlar.append("------------------------------------------------------------------------------------------------------------------------------" + "\n");


                            // buffer.append(gelenler.getValue(User.class).getName()+"\n   "+gelenler.getValue(User.class).getMesaj()+"\n\n");
                        }
                        //showMessage("Mesajlar",buffer.toString());
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
                alertDialogBuilder.setTitle("UYARI !");
                alertDialogBuilder
                        .setMessage("Çıkış Yapmak İstiyor Musunuz?")
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














    private void AddNewUser(String name, String mesaj) {
        DatabaseReference dbGidenler = db.getReference("MESSAGES");


        User kullanıcı = new User(name, mesaj);
        String key = dbGidenler.push().getKey();

        DatabaseReference dbGidenlerYeni = db.getReference("MESSAGES/"+key);

        dbGidenlerYeni.setValue(new User(name,mesaj));

    }



//    public void showMessage(String title,String Message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(Message);
//        builder.show();
//    }

}
