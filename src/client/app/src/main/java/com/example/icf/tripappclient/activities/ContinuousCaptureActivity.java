package com.example.icf.tripappclient.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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

import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketValidation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class ContinuousCaptureActivity extends Activity {
    private static final String TAG = ContinuousCaptureActivity.class.getSimpleName();
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    private SessionManager session;
    private Activity that = this;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            /*if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            lastText = result.getText();
            */

            //TODO: testiraj da li se gasi ekran - dvd
            Toast.makeText(that, "Scanned", Toast.LENGTH_SHORT).show();

            final TextView passengerCount = (TextView) findViewById(R.id.scanned_ticket_number_value);
            final TextView validity = (TextView) findViewById(R.id.scanned_ticket_validity_value);
            final TextView validThrough = (TextView) findViewById(R.id.scanned_ticket_date_value);

            TicketValidation validation = new TicketValidation();
            validation.setController(session.getUser());
            validation.setTicket(new io.swagger.client.model.TicketPurchase(result.getText()));

            Call<TicketPurchase> call = ServiceUtils.ticketValidationService.add(validation);
            call.enqueue(new Callback<TicketPurchase>() {

                @Override
                public void onResponse(Call<io.swagger.client.model.TicketPurchase> call, Response<TicketPurchase> response) {
                    if (response.code() == 200) {   // uspelo
                        io.swagger.client.model.TicketPurchase ticket = response.body();
                        beepManager.playBeepSound();
                        validity.setText("Valid");
                        passengerCount.setText(ticket.getNumberOfPassangers());
                        validThrough.setText(ticket.getEndDateTimeString());

                    } else if (response.code() == 406){

                        beepManager.playBeepSound();
                        validity.setText("Expired");


                    } else{
                        beepManager.playBeepSound();
                        validity.setText("Invalid");
                    }
                }

                @Override
                public void onFailure(Call<io.swagger.client.model.TicketPurchase> call, Throwable t) {
                    beepManager.playBeepSound();
                    validity.setText("Server error");
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

        setContentView(R.layout.continuous_scan);

        TextView passengerCountLabel = (TextView) findViewById(R.id.scanned_ticket_number_label);
        TextView validityLabel = (TextView) findViewById(R.id.scanned_ticket_validity_label);
        TextView validThroughLabel = (TextView) findViewById(R.id.scanned_ticket_date_label);

        passengerCountLabel.setText("Number of passengers:");
        validityLabel.setText("Ticket status:");
        validThroughLabel.setText("Valid through");


        barcodeView = (DecoratedBarcodeView) findViewById(R.id.barcode_scanner);
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
