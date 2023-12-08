package es.eps.ua.sharedprefeferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.eps.ua.sharedprefeferences.databinding.ActivityJavaIntroduceDataBinding;

public class JavaIntroduceDataActivity extends AppCompatActivity {
        private ActivityJavaIntroduceDataBinding viewBinding;
        private Button buttonApply;
        private Button buttonExit;
        private Button buttonSwap;
        private EditText editTextField;
        private TextView tamanyoLabel;
        private SeekBar seekBar;
        private TextView sizeResult;
        private TextView textResult;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                viewBinding = ActivityJavaIntroduceDataBinding.inflate(getLayoutInflater());
                setContentView(viewBinding.getRoot());

                buttonApply = viewBinding.applyChangesJava;
                buttonExit = viewBinding.exitButtonJava;
                buttonSwap = viewBinding.swapLanguageButtonJava;
                editTextField = viewBinding.editTextFieldJava;
                tamanyoLabel = viewBinding.siezeLabelTextJava;
                seekBar = viewBinding.seekBarIdJava;
                sizeResult = viewBinding.sizeResultJava;
                textResult = viewBinding.textResultJava;

                SharedPreferences pref = getSharedPreferences("preferencias", Context.MODE_PRIVATE);


                String prefText = UtilsJava.decrypt(pref.getString("textField", "Texto por Defecto"));
                float sizeText = Float.parseFloat(UtilsJava.decrypt(pref.getString("sizeText", "32")));

                textResult.setText(prefText);
                sizeResult.setText(String.valueOf(sizeText));



                editTextField.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                       /*         if (!s.toString().isEmpty()) {
                                        textResult.setText(editTextField.getText());
                                }
                       */

                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                });

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                Integer value = seekBar.getProgress();
                                if (value == 0) {
                                        value = 1;
                                }
                                tamanyoLabel.setText(getString(R.string.tamano) + " (" + value + "/50)");
                        }


                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                });

                buttonApply.setOnClickListener(v -> {
                        Intent intentCreateChatRoom = new Intent(JavaIntroduceDataActivity.this, JavaDetailActivity.class);
                        SharedPreferences prefs = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("textField", UtilsJava.encrypt(editTextField.getText().toString()));
                        Integer value = seekBar.getProgress();
                        if (value == 0) {
                                value = 1;
                        }
                        editor.putString("sizeText", UtilsJava.encrypt(value.toString()));
                        editor.apply();
                        startActivity(intentCreateChatRoom);
                });

                buttonSwap.setOnClickListener(v -> {
                        finish();

                });

                buttonExit.setOnClickListener(v -> finish());
        }
}


