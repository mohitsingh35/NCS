package com.mohit.ncs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mohit.ncs.databinding.ActivityThirdBinding;

public class ThirdActivity extends AppCompatActivity {
    Button button;
    ActivityThirdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView textView=findViewById(R.id.textView2);
        button= binding.homeButton;
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        Intent intent=getIntent();
        String item= intent.getStringExtra("selectedItem");
        textView.setText(item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ThirdActivity.this,MainActivity.class);
                startActivity(intent1);

            }
        });

    }
}