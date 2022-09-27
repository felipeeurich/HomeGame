package com.olin.homegame.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.olin.homegame.R;
import com.olin.homegame.config.ConfiguracaoFirebase;
import com.olin.homegame.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextView text_tela_cadastro;
    private TextInputEditText campoEmail, campoSenha;
    private FirebaseAuth auth;

    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        IniciarComponentes();

        auth = ConfiguracaoFirebase.getFirebaseAutenticacao();

        // Botão de cadastrar novo usuário
        text_tela_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this,CadastroActivity.class);
                startActivity(intent);

            }
        });

    }
    public void loginUsuario(View view){

        Usuario usuario = new Usuario();

        usuario.setEmail( campoEmail.getText().toString());
        usuario.setSenha(campoSenha.getText().toString());

        if(usuario.getEmail().isEmpty()){
            Toast.makeText(LoginActivity.this, "Preencha o email do usuário", Toast.LENGTH_SHORT).show();
        }else if(usuario.getSenha().isEmpty()){
            Toast.makeText(LoginActivity.this, "Preencha a senha do usuário", Toast.LENGTH_SHORT).show();
        }else{

            auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        abrirMain();
                    }else{

                        String excecao = "";

                        try {
                            throw  task.getException();
                        }catch (FirebaseAuthInvalidUserException e){
                            excecao = "Usuário não cadastrado!";
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            excecao = "Email ou senha não correspondem";
                        }catch (Exception e){
                            excecao = "Erro: " + e.getMessage();
                        }
                        Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();

                    }
                }
            });



        }
    }

    public void abrirMain(){

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void IniciarComponentes(){
        text_tela_cadastro = findViewById(R.id.text_tela_cadastro);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);



    }
}
