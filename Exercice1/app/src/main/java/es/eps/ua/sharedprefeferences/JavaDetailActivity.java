package es.eps.ua.sharedprefeferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import es.eps.ua.sharedprefeferences.databinding.ActivityJavaDetailBinding;
import es.eps.ua.sharedprefeferences.databinding.ActivityKotlinDetailBinding;

public class JavaDetailActivity extends AppCompatActivity {
    private ActivityJavaDetailBinding viewBinding;
    private Button buttonBack;
    private TextView textDetailJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityJavaDetailBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        buttonBack = viewBinding.exitButtonJava;
        textDetailJava = viewBinding.textDetailJava;
        SharedPreferences prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        textDetailJava.setText(UtilsJava.decrypt(prefs.getString("textField", "Texto por defecto")));
        textDetailJava.setTextSize(Float.parseFloat(UtilsJava.decrypt(prefs.getString("sizeText", "32"))));
        buttonBack.setOnClickListener(v -> finish());


        buttonBack.setOnClickListener(v -> {
            Intent intentCreateChatRoom = new Intent(JavaDetailActivity.this, JavaIntroduceDataActivity.class);
            intentCreateChatRoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentCreateChatRoom);
        });
    }
}


