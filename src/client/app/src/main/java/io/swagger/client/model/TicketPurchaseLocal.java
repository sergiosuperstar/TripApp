package io.swagger.client.model;

import com.example.icf.tripappclient.SessionManager;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by NemanjaM on 15.6.2017.
 */

@DatabaseTable(tableName = "Ticket")
@ApiModel(description = "")
public class TicketPurchaseLocal implements Serializable {

    @DatabaseField(id = true)
    @SerializedName("id")
    private Integer id = null;

    @DatabaseField
    @SerializedName("code")
    private String code = null;

    @DatabaseField
    @SerializedName("price")
    private Double price = null;

    @DatabaseField
    @SerializedName("startDateTime")
    private Date startDateTime = null;

    @DatabaseField
    @SerializedName("endDateTime")
    private Date endDateTime = null;

    @DatabaseField
    @SerializedName("numberOfPassangers")
    private Integer numberOfPassangers = null;

    @DatabaseField
    @SerializedName("type")
    private String typeString = null;

    @DatabaseField
    @SerializedName("userId")
    private Long userId = null;

    public TicketPurchaseLocal() {
    }

    public TicketPurchaseLocal(Integer id, String code, Double price, Date startDateTime,
                               Date endDateTime, Integer numberOfPassangers,
                               String typeString, Long userId) {
        this.id = id;
        this.code = code;
        this.price = price;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numberOfPassangers = numberOfPassangers;
        this.typeString = typeString;
        this.userId = userId;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
    @ApiModelProperty(required = true, value = "")
    public Date getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStartDateTimeString() {
        DateFormat df = new SimpleDateFormat(SessionManager.DATETIME_FORMAT);
        return df.format(startDateTime);
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public Date getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getEndDateTimeString() {
        DateFormat df = new SimpleDateFormat(SessionManager.DATETIME_FORMAT);
        return df.format(endDateTime);
    }

    public UUID getUUIDCode() {
        return UUID.fromString(code);
    }
    public void setUUIDCode(String guid) {
        code = guid;
    }

    /**
     * Number of passangers allowed to travel with buyer and including buyer too.
     **/
    @ApiModelProperty(value = "Number of passangers allowed to travel with buyer and including buyer too.")
    public Integer getNumberOfPassangers() {
        return numberOfPassangers;
    }
    public void setNumberOfPassangers(Integer numberOfPassangers) {
        this.numberOfPassangers = numberOfPassangers;
    }

    /**
     **/
    @ApiModelProperty(required = true, value = "")
    public String getTypeString() {
        return typeString;
    }
    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        io.swagger.client.model.TicketPurchase ticketPurchase = (io.swagger.client.model.TicketPurchase) o;
        return (this.id == null ? ticketPurchase.id == null : this.id.equals(ticketPurchase.id)) &&
                (this.code == null ? ticketPurchase.code == null : this.code.equals(ticketPurchase.code)) &&
                (this.price == null ? ticketPurchase.price == null : this.price.equals(ticketPurchase.price)) &&
                (this.startDateTime == null ? ticketPurchase.startDateTime == null : this.startDateTime.equals(ticketPurchase.startDateTime)) &&
                (this.endDateTime == null ? ticketPurchase.endDateTime == null : this.endDateTime.equals(ticketPurchase.endDateTime)) &&
                (this.numberOfPassangers == null ? ticketPurchase.numberOfPassangers == null : this.numberOfPassangers.equals(ticketPurchase.numberOfPassangers)) &&
                (this.type == null ? ticketPurchase.type == null : this.type.equals(ticketPurchase.type)) &&
                (this.user == null ? ticketPurchase.user == null : this.user.equals(ticketPurchase.user));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.code == null ? 0 : this.code.hashCode());
        result = 31 * result + (this.price == null ? 0 : this.price.hashCode());
        result = 31 * result + (this.startDateTime == null ? 0 : this.startDateTime.hashCode());
        result = 31 * result + (this.endDateTime == null ? 0 : this.endDateTime.hashCode());
        result = 31 * result + (this.numberOfPassangers == null ? 0 : this.numberOfPassangers.hashCode());
        result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
        result = 31 * result + (this.user == null ? 0 : this.user.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TicketPurchase {\n");

        sb.append("  id: ").append(id).append("\n");
        sb.append("  code: ").append(code).append("\n");
        sb.append("  price: ").append(price).append("\n");
        sb.append("  startDateTime: ").append(startDateTime).append("\n");
        sb.append("  endDateTime: ").append(endDateTime).append("\n");
        sb.append("  numberOfPassangers: ").append(numberOfPassangers).append("\n");
        sb.append("  type: ").append(type).append("\n");
        sb.append("  user: ").append(user).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
    */
}
