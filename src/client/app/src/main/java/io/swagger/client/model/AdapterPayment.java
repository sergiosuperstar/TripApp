package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by NemanjaM on 23.5.2017.
 */

@DatabaseTable(tableName = "Payment")
@ApiModel(description = "")
public class AdapterPayment implements Serializable {

    @DatabaseField(id = true, generatedId = true)
    @SerializedName("paymentId")
    private Integer paymentId = null;

    @DatabaseField
    @SerializedName("price")
    private Double price = null;

    @DatabaseField
    @SerializedName("endDateTime")
    private Date endDateTime = null;

    @DatabaseField
    @SerializedName("ticketName")
    private String ticketName = null;

    @DatabaseField
    @SerializedName("isExpense")
    private boolean isExpense = false;

    public AdapterPayment() {

    }

    public AdapterPayment(Double price, Date endDateTime, String ticketName, boolean isExpense) {
        this.paymentId = paymentId;
        this.price = price;
        this.endDateTime = endDateTime;
        this.ticketName = ticketName;
        this.isExpense = isExpense;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public Integer getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public Date getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public String getTicketName() {
        return ticketName;
    }
    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    /**
     **/
    @ApiModelProperty(value = "")
    public boolean getIsExpense() {
        return isExpense;
    }
    public void setIsExpense(boolean isExpense) {
        this.isExpense = isExpense;
    }


}
