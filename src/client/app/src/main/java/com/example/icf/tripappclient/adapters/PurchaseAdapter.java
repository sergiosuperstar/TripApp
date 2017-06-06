package com.example.icf.tripappclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.icf.tripappclient.R;

import java.util.List;

import io.swagger.client.model.AdapterPayment;
import io.swagger.client.model.TicketType;

/**
 * Created by NemanjaM on 6.6.2017.
 */

public class PurchaseAdapter extends ArrayAdapter<TicketType> {

    private Context context;
    private List<TicketType> types;

    public PurchaseAdapter(Context context, List<TicketType> types) {
        super(context, R.layout.single_ticket_type ,types);
        this.context = context;
        this.types = types;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_ticket_type, parent, false);

        Button ticket_button = (Button) view.findViewById(R.id.ticket_button);
        String title = types.get(position).getName();
        ticket_button.setText(title);

        return view;
    }
}
