package com.lance.motiondesigninterview.domainObjects;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/***
 * A {@link Pack} contains a list of {@link Item}s. It is guaranteed that all {@link Item}s in the same pack should have
 * unique item ids. It is possible that items from two different {@code Pack}s have same {@link Item#id}.
 */
@Getter
public class Pack {
    private static int nextPackId = 1;
    private int id = nextPackId++;
    private List<Item> items = new ArrayList<>();

    public int getItemNumber() {
        return items.stream().map(Item::getQuantity).reduce(Integer::sum).orElse(0);
    }

    public float getWeight() {
        return items.stream().map(item -> item.getQuantity() * item.getWeight()).reduce(Float::sum).orElse(0f);
    }

    public int getPackLength() {
        return items.stream().map(Item::getLength).reduce(Integer::max).orElse(0);
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void print() {
        System.out.println(String.format("Pack Number:%s", id));
        items.forEach(Item::print);
        System.out.println(String.format("Pack Length: %s, Pack Weight: %.3f", this.getPackLength(), this.getWeight()));
        System.out.println("\n");
    }
}
