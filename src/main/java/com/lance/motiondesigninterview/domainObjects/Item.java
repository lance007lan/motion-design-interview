package com.lance.motiondesigninterview.domainObjects;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/***
 * A {@link Item} is not necessarily a item in normal life. Instead, it is a group of items of same type, ie. same length
 * and same weight, and can be put into to one or more packs.
 */
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
