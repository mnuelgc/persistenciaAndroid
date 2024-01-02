package es.ua.eps.exercice3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

import es.ua.eps.exercice3.databinding.ActivityMainBinding;
import es.ua.eps.exercice3.databinding.ActivityShowInfoBinding;

public class MainActivity extends AppCompatActivity {

    final String INFO_TYPE = "INFO_TYPE";
    final String LOCATION_TYPE = "LOCATION_TYPE";     //if 0 local if 1 external

    ActivityMainBinding viewBinding;

    TextView infoTextView;
    EditText textToSave;

    Button seeExternalstateButton;
    Button saveText;
    Button seeFileButton;

    Button moveToExternal;
    Button moveToInternal;

    Button closeButton;


    boolean sdAvaible = false;
    boolean sdWriteAccess = false;
    boolean checkExternalStorage = false;

    int locationType = 0;

    String filePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());


        infoTextView = viewBinding.moveToText;

        textToSave = viewBinding.textToSave;
        seeExternalstateButton = viewBinding.seeExternalStorage;
        saveText = viewBinding.addFile;
        seeFileButton = viewBinding.seeFile;
        closeButton = viewBinding.closeButton;

        moveToInternal = viewBinding.internalStorageButton;
        moveToExternal = viewBinding.externalStorageButton;

        moveToInternal.setEnabled(false);
        seeExternalstateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ShowInfoActivity.class);
                intent.putExtra(INFO_TYPE, 0);
                startActivity(intent);
            }
        });

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveText();
            }
        });

        seeFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ShowInfoActivity.class);
                intent.putExtra(INFO_TYPE, 1);
                intent.putExtra(LOCATION_TYPE, locationType);
                startActivity(intent);
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        moveToInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeToInternal();
            }
        });

        moveToExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permisions()) {
                    ChangeToExternal();
                }
            }
        });

    }

    private Boolean existFile(String[] files, String fileName) {
        for (String file : files) {
            if (Objects.equals(file, fileName)) {
                return true;
            }
        }
        return false;
    }


    private void SaveText() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            sdAvaible = true;
            sdWriteAccess = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdAvaible = true;
            sdWriteAccess = false;
        }

        try {

            File sdPath = null;
            if (locationType == 0)
            {
                sdPath = getFilesDir();
            }
            else {
                sdPath = Environment.getExternalStorageDirectory();
            }

            File f = new File(sdPath, "information.txt");

            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f));
            fout.write(textToSave.getText().toString());
            fout.flush();
            fout.close();

        } catch (Exception e) {

        }
    }

    private Boolean permisions() {
        if (Build.VERSION.SDK_INT >= 30) {
            if (Environment.isExternalStorageManager() == false) {
                Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
                checkExternalStorage = true;
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Build.VERSION.SDK_INT >= 30) {
            if(checkExternalStorage) {
                checkExternalStorage = false;
                if(Environment.isExternalStorageManager()) {
                   ChangeToExternal();
                }
            }
        }
    }

    void MoveFromLocalToExternal(){MoveFile(getFilesDir(), Environment.getExternalStorageDirectory());}
    void MoveFromExternalToLocal(){MoveFile(Environment.getExternalStorageDirectory(), getFilesDir());}

    void MoveFile(File originDir, File destinyDir){
        try{
            File originFile = new File(originDir,"information.txt" );
            File destinyFile = new File (destinyDir, "information.txt");

            FileInputStream fileInputStream = new FileInputStream(originFile);
            FileOutputStream fileOutputStream = new FileOutputStream(destinyFile);

            byte [] buffer = new byte [1024];
            int length = fileInputStream.read(buffer);
            while (length > 0){
                fileOutputStream.write(buffer, 0, length);
                length = fileInputStream.read(buffer);
            }

            fileInputStream.close();
            fileOutputStream.close();

            originFile.delete();



        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void ChangeToExternal()
    {
        locationType = 1;
        MoveFromLocalToExternal();
        moveToInternal.setEnabled(true);
        moveToExternal.setEnabled(false);
    }

    private void ChangeToInternal()
    {
        locationType = 0;
        MoveFromExternalToLocal();
        moveToInternal.setEnabled(false);
        moveToExternal.setEnabled(true);
    }

}