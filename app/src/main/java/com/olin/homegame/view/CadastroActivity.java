package com.olin.homegame.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.olin.homegame.R;
import com.olin.homegame.config.ConfiguracaoFirebase;
import com.olin.homegame.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private Button button2;
    private TextInputEditText campoNome, campoEmail, campoSenha, campoTelefone;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().hide();
        IniciarComponentes();



    }

    public void cadastroUsuario(View view){
        Usuario usuario = new Usuario();

        usuario.setNome(campoNome.getText().toString());
        usuario.setEmail(campoEmail.getText().toString());
        usuario.setSenha(campoSenha.getText().toString());
        usuario.setTelefone(campoTelefone.getText().toString());

        if(usuario.getNome().isEmpty()){
            Toast.makeText(CadastroActivity.this, "Preencha o Nome do usuário", Toast.LENGTH_SHORT).show();
        }else if(usuario.getEmail().isEmpty()){
            Toast.makeText(CadastroActivity.this, "Preencha o email do usuário", Toast.LENGTH_SHORT).show();
        }else if(usuario.getSenha().isEmpty()){
            Toast.makeText(CadastroActivity.this, "Preencha a senha do usuário", Toast.LENGTH_SHORT).show();
        }else if(usuario.getTelefone().isEmpty()){
            Toast.makeText(CadastroActivity.this, "Preencha o número de celular", Toast.LENGTH_SHORT).show();
        }else{

            auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
            auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(CadastroActivity.this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();
                    }else{
                        String excecao = "";

                        try {
                            throw  task.getException();
                        }catch (FirebaseAuthWeakPasswordException e){
                            excecao = "Digite uma senha mais forte!";
                        }catch (FirebaseAuthInvalidCredentialsException e){
                            excecao = "Digite um email válido!";
                        }catch (FirebaseAuthUserCollisionException e){
                            excecao = "Email ja cadastrado!";
                        }catch (Exception e){
                            excecao = "Erro ao cadastrar o usuário" + e.getMessage();
                        }
                        Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();


                    }

                }
            });

        }


    }


    private void IniciarComponentes(){

        button2 = findViewById(R.id.button_cadastrar);
        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        campoTelefone = findViewById(R.id.editTelefone);
    }


    public void abrirMain(){

        Intent intent = new Intent(CadastroActivity.this,MainActivity.class);
        startActivity(intent);
    }
}