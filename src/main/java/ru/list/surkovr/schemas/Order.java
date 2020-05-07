package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    @SerializedName("Uuid")
    private String uuid;
    @SerializedName("OrderUuid")
    private String orderUuid;
    @SerializedName("Exchange")
    private String exchange;
    @SerializedName("OrderType")
    private String orderType; // Enum LIMIT_BUY, LIMIT_SELL
    @SerializedName("Quantity")
    private double quantity;
    @SerializedName("QuantityRemaining")
    private double quantityRemaining;
    @SerializedName("Limit")
    private double limit;
    @SerializedName("CommissionPaid")
    private double commissionPaid;
    @SerializedName("Price")
    private double price;
    @SerializedName("PricePerUnit")
    private double pricePerUnit;
    @SerializedName("Opened")
    private String opened;
    @SerializedName("Closed")
    private boolean closed;
    @SerializedName("CancelInitiated")
    private boolean cancelInitiated;
    @SerializedName("ImmediateOrCancel")
    private boolean immediateOrCancel;
    @SerializedName("IsConditional")
    private boolean isConditional;
    @SerializedName("Condition")
    private Object condition;
    @SerializedName("ConditionTarget")
    private Object conditionTarget;
}