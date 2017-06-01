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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
        super(context, R.layout.single_payment ,payments);
        this.context = context;
        this.payments = payments;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_payment, parent, false);

        TextView sign_minus = (TextView) view.findViewById(R.id.payment_sign_minus);
        TextView sign_plus = (TextView) view.findViewById(R.id.payment_sign_minus);
        TextView value_minus = (TextView) view.findViewById(R.id.payment_value_minus);
        TextView value_plus = (TextView) view.findViewById(R.id.payment_value_plus);
        TextView currency_minus = (TextView) view.findViewById(R.id.payment_currency_minus);
        TextView currency_plus = (TextView) view.findViewById(R.id.payment_currency_plus);
        TextView type_ticket = (TextView) view.findViewById(R.id.payment_ticket);
        TextView date_value = (TextView) view.findViewById(R.id.payment_date);

        boolean isExpense = payments.get(position).getIsExpense();
        double value = payments.get(position).getPrice();
        Date date = payments.get(position).getEndDateTime();

        String valueString = String.format("%.2f", value);
        String dateString = new SimpleDateFormat("dd.MM.'yy (HH:mm)").format(date);
        String ticket;

        if (isExpense) {
            ticket = " ";
            sign_minus.setVisibility(View.VISIBLE);
            sign_plus.setVisibility(View.GONE);
            value_minus.setVisibility(View.VISIBLE);
            value_plus.setVisibility(View.GONE);
            currency_minus.setVisibility(View.VISIBLE);
            currency_plus.setVisibility(View.GONE);

            type_ticket.setText(ticket);
            value_minus.setText(valueString);
        } else {
            ticket = payments.get(position).getTicketName();
            sign_minus.setVisibility(View.GONE);
            sign_plus.setVisibility(View.VISIBLE);
            value_minus.setVisibility(View.GONE);
            value_plus.setVisibility(View.VISIBLE);
            currency_minus.setVisibility(View.GONE);
            currency_plus.setVisibility(View.VISIBLE);

            type_ticket.setText(ticket);
            value_plus.setText(valueString);
        }
        date_value.setText(dateString);


        return view;
    }
}
