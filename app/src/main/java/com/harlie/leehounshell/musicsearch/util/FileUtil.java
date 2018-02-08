package com.harlie.leehounshell.musicsearch.util;

import com.harlie.leehounshell.musicsearch.view.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    private final static String TAG = "LEE: <" + BaseActivity.class.getSimpleName() + ">";

    public static String convertStreamToString(java.io.InputStream is) {
        LogHelper.v(TAG, "convertStreamToString");
        StringBuilder buf = new StringBuilder();
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String str;
            try {
                while ((str = reader.readLine()) != null) {
                    buf.append(str).append("\n");
                }
            }
            catch (IOException e) {
                // NOTE: println is here because LogHelper output is hidden from Unit Tests
                System.err.println("*** unable to read JSON test data! *** - e=" + e);
                LogHelper.e(TAG, "*** unable to read JSON test data! *** - e=" + e);
            }
        }
        else {
            LogHelper.e(TAG, "*** the InputStream is null! ***");
        }
        return buf.toString();
    }
}
