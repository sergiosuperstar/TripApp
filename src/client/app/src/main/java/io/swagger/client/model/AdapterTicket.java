package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by NemanjaM on 23.5.2017.
 */

@ApiModel(description = "")
public class AdapterTicket implements Serializable {

    @SerializedName("ticketId")
    private Integer ticketId = null;
    @SerializedName("ticketName")
    private String ticketName = null;
    @SerializedName("price")
    private Double price = null;
    @SerializedName("endDateTime")
    private Date endDateTime = null;

    /**
     **/
    @ApiModelProperty(value = "")
    public Integer getTicketId() {
        return ticketId;
    }
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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


}
