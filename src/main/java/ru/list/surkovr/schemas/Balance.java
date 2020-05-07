package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Balance {
    @SerializedName("Currency")
    private String currency;
    @SerializedName("Balance")
    private double balance;
    @SerializedName("Available")
    private double available;
    @SerializedName("Pending")
    private double pending;
    @SerializedName("CryptoAddress")
    private String cryptoAddress;
    @SerializedName("Requested")
    private boolean requested;
    @SerializedName("Uuid")
    private String uuid;
}