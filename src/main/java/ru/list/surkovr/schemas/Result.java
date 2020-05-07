package ru.list.surkovr.schemas;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Result<T> {

    private boolean success;
    private String message;
    private T result;
}