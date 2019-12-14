package com.example.hallelujah.Mpesa;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hallelujah.Mpesa.model.AccessToken;
import com.example.hallelujah.Mpesa.model.STKPush;
import com.example.hallelujah.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.example.hallelujah.Mpesa.AppConstants.BUSINESS_SHORT_CODE;
import static com.example.hallelujah.Mpesa.AppConstants.CALLBACKURL;
import static com.example.hallelujah.Mpesa.AppConstants.PARTYB;
import static com.example.hallelujah.Mpesa.AppConstants.PASSKEY;
import static com.example.hallelujah.Mpesa.AppConstants.TRANSACTION_TYPE;

public class TitheActivity extends AppCompatActivity implements View.OnClickListener {
    private ApiClient mApiClient;
    private ProgressDialog mProgressDialog;

    @BindView(R.id.number2) EditText number2;
    @BindView(R.id.number)
    EditText number;
    @BindView(R.id.amount) EditText amount;
    @BindView(R.id.sendMoneyBtn)
    Button mPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tithe);
        ButterKnife.bind(this);

        mProgressDialog = new ProgressDialog(this);
        mApiClient = new ApiClient();
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.

        mPay.setOnClickListener(this);

        getAccessToken();
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    mApiClient.setAuthToken(response.body().accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mPay){
            String phone_number = number.getText().toString();
            String numberTwo = number2.getText().toString();
            int amounts = Integer.parseInt(String.valueOf(amount.getText()));
            String payamount = String.valueOf(amounts/2);
            String amount1 = amount.getText().toString();
            performSTKPush(phone_number,payamount);
            performSTKPush(numberTwo,payamount);
        }
    }
    public void performSTKPush(String phone_number,String amount) {
        mProgressDialog.setMessage("Processing your request");
        mProgressDialog.setTitle("Please Wait...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
        String timestamp = utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(amount),
                utils.sanitizePhoneNumber(phone_number),
                PARTYB,
                utils.sanitizePhoneNumber(phone_number),
                CALLBACKURL,
                "test", //The account reference
                "test"  //The transaction description
        );

        mApiClient.setGetAccessToken(false);

        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {
                mProgressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        Timber.d("post submitted to API. %s", response.body());
                    } else {
                        Timber.e("Response %s", response.errorBody().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
                Timber.e(t);
            }
        });
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
