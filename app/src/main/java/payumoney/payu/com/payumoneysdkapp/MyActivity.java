package payumoney.payu.com.payumoneysdkapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payUMoney.sdk.PayUmoneySdkInitilizer;
import com.payUMoney.sdk.SdkConstants;
import com.payUMoney.sdk.utils.SdkHelper;

import java.util.HashMap;

/**
 * Created by Sanwal Singh on 19/5/16.
 */
public class MyActivity extends Activity {


    EditText amt = null, txnid = null, phone = null, pinfo = null, fname = null,
            email = null, surl = null, furl = null, mid = null, udf1 = null,
            udf2 = null, udf3 = null, udf4 = null, udf5 = null;
    Button pay = null;
    String amount;

    public static final String TAG = "PayUMoneySDK Sample";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //setContentView(R.layout.activity_my);
        amt = (EditText) findViewById(R.id.amount);
        txnid = (EditText) findViewById(R.id.txnid);
        mid = (EditText) findViewById(R.id.merchant_id);
        phone = (EditText) findViewById(R.id.phone);
        pinfo = (EditText) findViewById(R.id.pinfo);
        fname = (EditText) findViewById(R.id.fname);
        email = (EditText) findViewById(R.id.email);
        surl = (EditText) findViewById(R.id.surl);
        furl = (EditText) findViewById(R.id.furl);
        udf1 = (EditText) findViewById(R.id.udf1);
        udf2 = (EditText) findViewById(R.id.udf2);
        udf3 = (EditText) findViewById(R.id.udf3);
        udf4 = (EditText) findViewById(R.id.udf4);
        udf5 = (EditText) findViewById(R.id.udf5);
        furl = (EditText) findViewById(R.id.furl);
        pay = (Button) findViewById(R.id.pay);
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "--------0000000--------");
        if (requestCode == PayUmoneySdkInitilizer.PAYU_SDK_PAYMENT_REQUEST_CODE) {
            Log.e(TAG, "--------1111111--------");
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "--------Success - Payment ID-------- : " + data.getStringExtra(SdkConstants.PAYMENT_ID));
                String paymentId = data.getStringExtra(SdkConstants.PAYMENT_ID);
                String date = data.getStringExtra(SdkConstants.TRANSACTION_DATE);
                String pay_type = data.getStringExtra(SdkConstants.TRANSACTION_DATE);
                showDialogMessage("Payment Success Id : " + paymentId);
                //getPerspective().addThankYouFragment(paymentId, date, pay_type);

            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "--------RESULT CANCEL--------");
                showDialogMessage("cancelled");
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_FAILED) {
                Log.d("app_activity", "--------failure--------");
                if (data != null) {
                    if (data.getStringExtra(SdkConstants.RESULT).equals("cancel")) {

                    } else {
                        showDialogMessage("failure");
                    }
                }
                //Write your code if there's no result
            } else if (resultCode == PayUmoneySdkInitilizer.RESULT_BACK) {
                Log.i(TAG, "User returned without login");
                showDialogMessage("User returned without login");
            }
        }
    }

    private void showDialogMessage(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TAG);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    public void makePayment(View view) {
        paymentMethod();
    }

    private void paymentMethod() {

        Double amt = 0.0;
        amount = "1";
        if (isDouble(amount)) {
            amt = Double.parseDouble(amount);
        } else {
            Toast.makeText(this, "Enter correct amount",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (amt <= 0.0) {
            Toast.makeText(this, "Enter Some amount", Toast.LENGTH_LONG).show();
            return;
        } else if (amt > 1000000.00) {
            Toast.makeText(this, "Amount exceeding the limit : 1000000.00 ",
                    Toast.LENGTH_LONG).show();
            return;
        } else {

            PayUmoneySdkInitilizer.PaymentParam.Builder builder = new PayUmoneySdkInitilizer.PaymentParam.Builder();

            builder.setKey("PXDlMr"); //Put your live KEY here
            builder.setSalt("xr7VixIg"); //Put your live SALT here
            builder.setMerchantId("5098948");

            /*builder.setIsDebug(false);
            builder.setDebugKey("F4Vvyz");// Debug Key
            builder.setDebugMerchantId("4828127");// Debug Merchant ID
            builder.setDebugSalt("Z6cEj6SP");// Debug Salt*/

            builder.setAmount(Double.parseDouble("1"));
            builder.setTnxId("6371");
            builder.setPhone("7742526633");// debug
            builder.setProductName("opencart products information");// debug
            builder.setFirstName("Sanwal");// debug
            builder.setEmail("shanwal2009@gmail.com");// debug
            builder.setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php");
            builder.setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php");
            builder.setUdf2("6371");

            PayUmoneySdkInitilizer.PaymentParam paymentParam = builder.build();
            PayUmoneySdkInitilizer.startPaymentActivityForResult(this, paymentParam);
        }

    }

}
