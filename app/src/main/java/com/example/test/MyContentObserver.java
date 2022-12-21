package com.example.test;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MyContentObserver extends ContentObserver {
    Context c;
    String number, message;


    public MyContentObserver(Handler h, Context c, String number, String message) {
        super(h);
        this.c = c;
        this.number = number;
        this.message = message;
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Cursor cursor = c.getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
        if (cursor.moveToFirst()) {
            int n = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            String num = cursor.getString(n);
            Log.d("Observer", num);
            Log.d("Observer", number);
            Log.d("Observer", message);
            SmsManager smsManager = SmsManager.getDefault();
            if(message.length() != 0 && num.equals(number)){
                smsManager.sendTextMessage(number, null, message, null, null);
                Toast.makeText(c, "You have been called from +6505551212", Toast.LENGTH_LONG).show();
            }
        }
    }
}
