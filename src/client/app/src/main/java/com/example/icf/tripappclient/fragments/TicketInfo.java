package com.example.icf.tripappclient.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icf.tripappclient.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Date;
import java.util.UUID;

import io.swagger.client.model.*;
import io.swagger.client.model.TicketPurchase;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketInfo extends Fragment {

    public TicketInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.ticket_info_title);

        View view=inflater.inflate(R.layout.fragment_ticket_info, container, false);



        //TODO: get ticket
        TicketPurchase ticket = new TicketPurchase();
        TicketType type = new TicketType();
        type.setName("Daily ticket");
        ticket.setCode(UUID.randomUUID());
        ticket.setNumberOfPassangers(3);
        ticket.setPrice(1.20);
        ticket.setStartDateTime(new Date());
        ticket.setEndDateTime(new Date(ticket.getStartDateTime().getTime()+(24*60*60*1000)));
        ticket.setType(type);
        /* *************** */



        ((TextView) view.findViewById(R.id.dateFromValue)).setText(ticket.getStartDateTimeString());
        ((TextView) view.findViewById(R.id.dateToValue)).setText(ticket.getEndDateTimeString());
        ((TextView) view.findViewById(R.id.ticketPriceValue)).setText(ticket.getPrice().toString() + " EUR");
        ((TextView) view.findViewById(R.id.ticketsNumberValue)).setText(ticket.getNumberOfPassangers().toString());
        ((TextView) view.findViewById(R.id.ticketTypeValue)).setText(ticket.getType().getName());

        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.qrExample);
            Bitmap bitmap = encodeAsBitmap(ticket.getCode().toString());
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return view;
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 150, 150, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 150, 0, 0, w, h);
        return bitmap;
    }

}
