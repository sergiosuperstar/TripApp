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

import io.swagger.client.model.AdapterPayment;

/**
 * Created by NemanjaM on 1.6.2017.
 */

public class PaymentAdapter extends ArrayAdapter<AdapterPayment> {

    private Context context;
    private List<AdapterPayment> payments;

    public PaymentAdapter(Context context, List<AdapterPayment> payments) {
        super(context, R.layout.single_payment_plus,payments);
        this.context = context;
        this.payments = payments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;

        boolean isExpense = payments.get(position).getIsExpense();
        double value = payments.get(position).getPrice();
        Date date = payments.get(position).getEndDateTime();

        String valueString = String.format("%.2f", value);
        //String dateString = new SimpleDateFormat("dd.MM.'yy (HH:mm)").format(date);
        String dateString = payments.get(position).getEndDateTimeString();
        String ticket;

        if (isExpense) {
            view = inflater.inflate(R.layout.single_payment_minus, parent, false);
        } else {
            view = inflater.inflate(R.layout.single_payment_plus, parent, false);
        }

        TextView value_price = (TextView) view.findViewById(R.id.payment_value);
        TextView type_ticket = (TextView) view.findViewById(R.id.payment_ticket);
        TextView date_value = (TextView) view.findViewById(R.id.payment_date);


        if (isExpense) {
            ticket = payments.get(position).getTicketName();
            type_ticket.setText(ticket);
            value_price.setText(valueString);
        } else {
            ticket = " ";
            type_ticket.setText(ticket);
            value_price.setText(valueString);
        }
        date_value.setText(dateString);


        return view;
    }
}
