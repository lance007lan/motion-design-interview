package com.lance.motiondesigninterview.domainObjects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@Builder
@EqualsAndHashCode
public class Item {
    private int id;
    private int length;
    private int quantity;
    private float weight;

    public void print() {
        System.out.println(String.format("%s,%s,%s,%.3f", id, length, quantity, weight));
    }
}
