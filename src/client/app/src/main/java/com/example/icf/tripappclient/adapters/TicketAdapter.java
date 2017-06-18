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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketPurchaseLocal;

/**
 * Created by NemanjaM on 1.6.2017.
 */

public class TicketAdapter extends ArrayAdapter<TicketPurchaseLocal> {

    private Context context;
    private List<TicketPurchaseLocal> tickets;

    public TicketAdapter(Context context, List<TicketPurchaseLocal> tickets) {
        super(context, R.layout.single_ticket ,tickets);
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_ticket, parent, false);

        TextView value_date = (TextView) view.findViewById(R.id.ticket_expired);
        TextView value_type = (TextView) view.findViewById(R.id.ticket_type);
        TextView value_cost = (TextView) view.findViewById(R.id.ticket_price);

        double cost = tickets.get(position).getPrice();
        Date date = tickets.get(position).getEndDateTime();
        String ticket = tickets.get(position).getTypeString();
        int numberOfPassangers = tickets.get(position).getNumberOfPassangers();

        String dateValue = tickets.get(position).getEndDateTimeString();
        //String dateValue = new SimpleDateFormat("dd.MM.'yy (HH:mm)").format(date);
        String costValue = String.format("%.2f", cost * numberOfPassangers);

        value_date.setText(dateValue);
        value_type.setText(ticket);
        value_cost.setText(costValue);

        return view;
    }
}
