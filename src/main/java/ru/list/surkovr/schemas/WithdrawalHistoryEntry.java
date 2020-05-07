package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WithdrawalHistoryEntry {
    @SerializedName("PaymentUuid")
    private String paymentUuid;
    @SerializedName("Currency")
    private String currency;
    @SerializedName("Amount")
    private double amount;
    @SerializedName("Address")
    private String address;
    @SerializedName("Opened")
    private String opened;
    @SerializedName("Authorized")
    private boolean authorized;
    @SerializedName("PendingPayment")
    private boolean pendingPayment;
    @SerializedName("TxCost")
    private double txCost;
    @SerializedName("TxId")
    private String txId;
    @SerializedName("Canceled")
    private boolean canceled;
    @SerializedName("InvalidAddress")
    private boolean invalidAddress;
}
