package com.example.icf.tripappclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;
import com.example.icf.tripappclient.service.ServiceUtils;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;
import java.util.UUID;

import io.swagger.client.model.PurchaseCode;
import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketValidation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class VoucherScannerActivity extends Activity {
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private SessionManager session;
    private Activity that = this;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            String scanned = result.getText();

            //Toast.makeText(that, "Scanned: " + scanned, Toast.LENGTH_LONG).show();

            PurchaseCode code = new PurchaseCode();
            try {
                code.setCode(UUID.fromString(scanned));
                code.setUserId(session.getUser().getId());
            }catch(Exception e){
                Toast.makeText(that, "Failed to add funds. ", Toast.LENGTH_LONG).show();
            }

            Call<Boolean> call = ServiceUtils.purchaseCodeService.put(code);
            call.enqueue(new Callback<Boolean>() {

                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.code() == 200) {
                        Boolean resp = response.body();

                        if (resp){
                            Toast.makeText(that, "Successfully added funds.", Toast.LENGTH_LONG).show();
                            //session.reloadUserBalance((MainActivity) that);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("balance", true);
                            startActivity(intent);
                        }else{
                            Toast.makeText(that, "Failed to add funds. ", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(that, "Failed to add funds. ", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(that, "Failed to add funds. ", Toast.LENGTH_LONG).show();
                }
            });

        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());

        setContentView(R.layout.voucher_scanner);

        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner_2);
        barcodeView.decodeContinuous(callback);

        beepManager = new BeepManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
