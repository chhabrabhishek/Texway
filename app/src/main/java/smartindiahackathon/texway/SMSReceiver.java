package smartindiahackathon.texway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by abhishek on 25/3/18.
 */

public class SMSReceiver extends BroadcastReceiver {
    int i;
    String send_number,x,y;
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            for (i=0; i < pdus.length; i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdus[i]);
                send_number = smsMessage.getOriginatingAddress();
                String message = smsMessage.getDisplayMessageBody();
                Toast.makeText(context, "From : "+send_number+"  Message : "+message, Toast.LENGTH_LONG).show();

                 x = message.substring(message.lastIndexOf("=")+1);

            }

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(send_number,null,"Thank you server",null,null);
        }
    }

}
