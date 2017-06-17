package io.swagger.client.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NemanjaM on 17.6.2017.
 */

@DatabaseTable(tableName = "Scanned")
public class TicketScannedModel {

    @DatabaseField(generatedId = true)
    @SerializedName("id")
    private Integer id = null;

    @DatabaseField
    @SerializedName("scannedTime")
    private Date scannedTime = null;

    @DatabaseField
    @SerializedName("wasValid")
    private boolean wasValid = false;

    @DatabaseField
    @SerializedName("ticket")
    private String typeStringAndId = null;

    @DatabaseField
    @SerializedName("user")
    private String userString = null;


    public TicketScannedModel() {
    }

    public TicketScannedModel(Date scannedTime, boolean wasValid, String typeStringAndId, String userString) {
        this.scannedTime = scannedTime;
        this.wasValid = wasValid;
        this.typeStringAndId = typeStringAndId;
        this.userString = userString;
    }

    public Integer getId() {
        return id;
    }

    public Date getScannedTime() {
        return scannedTime;
    }

    public String getScannedTimeString() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return df.format(scannedTime);
    }

    public boolean isWasValid() {
        return wasValid;
    }

    public String getTypeStringAndId() {
        return typeStringAndId;
    }

    public String getUserString() {
        return userString;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setScannedTime(Date scannedTime) {
        this.scannedTime = scannedTime;
    }

    public void setWasValid(boolean wasValid) {
        this.wasValid = wasValid;
    }

    public void setTypeStringAndId(String typeStringAndId) {
        this.typeStringAndId = typeStringAndId;
    }

    public void setUserString(String userString) {
        this.userString = userString;
    }
}
