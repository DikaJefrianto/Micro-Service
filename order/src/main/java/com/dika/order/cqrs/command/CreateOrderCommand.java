package com.dika.order.cqrs.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {
    private long produkId;
    private long pelangganId;
    private int jumlah;
    private String tanggal;
    private String status;
    private double total;
}
