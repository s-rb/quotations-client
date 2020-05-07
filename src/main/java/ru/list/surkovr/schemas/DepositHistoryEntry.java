package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DepositHistoryEntry {

    @SerializedName("Id")
    private int id;
    @SerializedName("Confirmations")
    private long confirmations;
    @SerializedName("LastUpdated")
    private String lastUpdated;
    @SerializedName("CryptoAddress")
    private String cryptoAddress;
    @SerializedName("Currency")
    private String currency;
    @SerializedName("Amount")
    private double amount;
    @SerializedName("TxId")
    private String txId;
}
