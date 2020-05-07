package ru.list.surkovr.schemas;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Address {
    @SerializedName("Currency")
    private String currency;
    @SerializedName("Address")
    private String address;
}
