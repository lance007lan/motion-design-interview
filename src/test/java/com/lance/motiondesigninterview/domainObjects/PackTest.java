package com.lance.motiondesigninterview.domainObjects;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PackTest {
    @Test
    public void initializePack() {
        // given
        Pack pack = new Pack();

        // when
        // then
        assertThat(pack.getWeight()).isEqualTo(0f);
        assertThat(pack.getItemNumber()).isEqualTo(0);
    }

    @Test
    public void getTotalItemNumber() {
        // given
        Pack pack = new Pack();

        // when
        pack.addItem(Item.builder().id(2).quantity(2).length(2).weight(2f).build());
        pack.addItem(Item.builder().id(3).quantity(3).length(3).weight(3f).build());

        // then
        assertThat(pack.getItemNumber()).isEqualTo(5);
    }

    @Test
    public void getTotalWeight() {
        // given
        Pack pack = new Pack();

        // when
        pack.addItem(Item.builder().id(2).quantity(2).length(2).weight(2f).build());
        pack.addItem(Item.builder().id(3).quantity(3).length(3).weight(3f).build());

        // then
        assertThat(pack.getWeight()).isEqualTo(13f);
    }
}