package bctest.com.bctest;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by sakshamkeshri on 3/15/18.
 */

public class Utils {
    public static void readAndLog(String path){
        File file = new File(path);
        StringBuilder text = new StringBuilder();

        try {
            File sdcard = Environment.getExternalStorageDirectory();
            Log.d(MainActivity.TAG, "sdcard : " + sdcard.getAbsolutePath());
            for (File fileInternal : sdcard.listFiles()) {
                Log.d(MainActivity.TAG, "internal file : "+ fileInternal);
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            Log.d(MainActivity.TAG, "IOException in file reading");
            e.printStackTrace();
        }
        Log.d(MainActivity.TAG, "file name : " + path + " File contains : " + text);
    }
}
