package com.example.firebase_android2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btnledon, btnledoff;
    TextView ledstatus;
    ImageView ledimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnledon = findViewById(R.id.btnledon);
        btnledoff = findViewById(R.id.btnledoff);
        ledstatus = findViewById(R.id.ledstatus);
        ledimg = findViewById(R.id.ledimg);
        setTitle(("LED Remote Control"));

        // Firbase 연동 코드
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("LED_STATUS");

        myRef.setValue("default");
        ledstatus.setText(myRef.getKey());

        // 데이터 베이스에서 읽기 addValueEventListener 사용
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ledState = (String) dataSnapshot.getValue();
                ledstatus.setText("LED STATUS : " + ledState);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // 버튼 LED ON 클릭 시 이벤트 처리
        // 글자색을 빨간색으로 변경, imageview를 ledon으로 변경
        btnledon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledstatus.setTextColor(Color.RED);
                myRef.setValue("ON");
                ledimg.setImageResource(R.drawable.ledon);
            }
        });
        // 버튼 LED OFF 클릭 시 이벤트 처리
        // 글자색을 파란색으로 변경, imageview를 ledoff으로 변경
        btnledoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledstatus.setTextColor(Color.BLUE);
                myRef.setValue("OFF");
                ledimg.setImageResource(R.drawable.ledoff);
            }
        });
    }

}