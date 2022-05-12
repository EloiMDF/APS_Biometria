package br.unip.aps_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtMessage;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessage = findViewById(R.id.tv_msg);
        btnLogin = findViewById(R.id.btnAuth);



        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                txtMessage.setText("Biometria habilitada");
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                txtMessage.setText("Biometria indisponível");
                btnLogin.setVisibility(View.GONE);
                break;


            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                txtMessage.setText("Biometria não cadastrada");
                btnLogin.setVisibility(View.GONE);
                break;




        }
        BiometricPrompt prompt = new BiometricPrompt(this, ContextCompat.getMainExecutor(this), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                Toast.makeText(getApplicationContext(), "Falha!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toast.makeText(getApplicationContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
                txtMessage.setText("Sucesso!");
                txtMessage.setTextColor(Color.GREEN);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("APS BIOMETRIA")
                .setNegativeButtonText("Cancelar")
                .build();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.authenticate(promptInfo);
            }
        });


    }
}