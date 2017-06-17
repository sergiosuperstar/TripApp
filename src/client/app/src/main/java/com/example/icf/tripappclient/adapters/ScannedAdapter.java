package com.example.icf.tripappclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.icf.tripappclient.R;

import java.util.Date;
import java.util.List;

import io.swagger.client.model.TicketScannedModel;

/**
 * Created by NemanjaM on 17.6.2017.
 */

public class ScannedAdapter extends ArrayAdapter<TicketScannedModel> {

    private Context context;
    private List<TicketScannedModel> scanned;

    public ScannedAdapter(Context context, List<TicketScannedModel> scanned) {
        super(context, R.layout.single_ticket ,scanned);
        this.context = context;
        this.scanned = scanned;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        String scannedTime = scanned.get(position).getScannedTimeString();
        boolean wasValid = scanned.get(position).isWasValid();
        String typeStringAndId = scanned.get(position).getTypeStringAndId();
        String userString = scanned.get(position).getUserString();

        if (wasValid) {
            view = inflater.inflate(R.layout.single_scanned_valid, parent, false);
        } else {
            view = inflater.inflate(R.layout.single_scanned_expired, parent, false);
        }

        TextView value_date = (TextView) view.findViewById(R.id.date_scanned);
        TextView value_ticket = (TextView) view.findViewById(R.id.ticket_type_and_id);
        TextView value_user = (TextView) view.findViewById(R.id.user);

        value_date.setText(scannedTime);
        value_ticket.setText(typeStringAndId);
        value_user.setText(userString);

        return view;
    }
}
