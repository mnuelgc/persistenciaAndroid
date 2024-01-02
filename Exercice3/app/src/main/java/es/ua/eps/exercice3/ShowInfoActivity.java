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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import es.ua.eps.exercice3.databinding.ActivityShowInfoBinding;

public class ShowInfoActivity extends AppCompatActivity {

    final String INFO_TYPE = "INFO_TYPE";
    final String LOCATION_TYPE = "LOCATION_TYPE";     //if 0 local if 1 external

    ActivityShowInfoBinding viewBinding;

    TextView infoTextView;

    Button backButton;

    boolean checkExternalStorage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityShowInfoBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        backButton = viewBinding.backButton;

        infoTextView = viewBinding.textInfo;


        String state = getInfo();
        infoTextView.setText(getInfo());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getInfo(){
        String info = "";
        Intent intent = getIntent();
        switch (intent.getIntExtra(INFO_TYPE, -1))
        {
            case 0 :{
                info = ExternalSDInfo();
                break;
            }

            case 1: {
                if (intent.getIntExtra(LOCATION_TYPE, -1) == 0)
                    info = ReturnLocalText();

                if (intent.getIntExtra(LOCATION_TYPE, -1) == 1)
                    info = ReturnExternalText();
                break;
            }
            default:{
                info = "ERROR";
                break;
            }
        }

        return info;
    }

    private String ExternalSDInfo(){
        String info = "";

        info  = "State: " + Environment.getExternalStorageState() + "\n";
        info += "Emulated: " + Environment.isExternalStorageEmulated() + "\n";
        info += "Removable: " + Environment.isExternalStorageRemovable() + "\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            info += "Legacy: " + Environment.isExternalStorageLegacy() + "\n";
        }

        info += "Data Dir: " + getDataDir() + "\n";
        info += "Cache Dir: " + getApplicationContext().getCacheDir() + "\n";
        File sdPath = Environment.getExternalStorageDirectory();
        info += "External Storage Dir: " + String.valueOf(sdPath) + "\n";
        info += "External ALARMS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS) + "\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            info += "EXTERNAL AUDIOBOOKS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_AUDIOBOOKS) + "\n";
        }
        info += "EXTERNAL DCIM Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DCIM) + "\n";

        info += "EXTERNAL DOCUMENTS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "\n";
        info += "EXTERNAL DOWNLOADS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "\n";
        info += "EXTERNAL MOVIES Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES) + "\n";
        info += "EXTERNAL MUSIC Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "\n";
        info += "EXTERNAL NOTIFICATIONS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS) + "\n";
        info += "EXTERNAL PICTURES Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "\n";
        info += "EXTERNAL PODCASTS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PODCASTS) + "\n";
        info += "EXTERNAL RINGTONES Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_RINGTONES) + "\n";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            info += "EXTERNAL SCREENSHOTS Dir: " + getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_SCREENSHOTS) + "\n";
        }
        info += "Root Dir: " + Environment.getRootDirectory()+ "\n";
        return info;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Build.VERSION.SDK_INT >= 30) {
            if(checkExternalStorage) {
                checkExternalStorage = false;
            }
        }
    }

    private String ReturnStoredText(File dir)
    {
        File fileToRead = new File (dir, "information.txt");

        if (fileToRead.exists())
        {
            try {
                FileInputStream inputStream = new FileInputStream(fileToRead);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder text = new StringBuilder();

                String line;
                do{
                    line = bufferedReader.readLine();

                    if (line!=null) text.append(line).append("\n");
                } while (line != null);

                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                return text.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return "El fichero no existe";
    }

    private String ReturnLocalText() {return ReturnStoredText(getFilesDir());}
    private String ReturnExternalText() {return ReturnStoredText(Environment.getExternalStorageDirectory());}

}