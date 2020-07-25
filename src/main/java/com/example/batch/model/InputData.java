package com.example.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InputData {

    private Long id;
    private String subscriberId;
    private String productName;
    private BigDecimal amount;
}
