package com.olin.homegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CadastroActivity extends AppCompatActivity {

    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        IniciarComponentes();


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CadastroActivity.this,Cadastro2Activity.class);
                startActivity(intent);
            }
        });

    }
    private void IniciarComponentes(){
        button2 = findViewById(R.id.button2);
    }
}