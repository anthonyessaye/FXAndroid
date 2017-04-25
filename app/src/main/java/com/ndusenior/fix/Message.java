package com.ndusenior.fix;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by antho on 4/13/2017.
 */

public class Message {

    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showToast(final String toast, final Activity theActivity)
    {
        theActivity.runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(theActivity.getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
