package com.lance.motiondesigninterview;

import com.lance.motiondesigninterview.domainObjects.*;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PackerTest {
    private Packer packer = new Packer();

    private PackingCommand createExamplePackingCommand(SortOrder sortOrder) {
        return PackingCommand.builder()
                .packingCriteria(PackingCriteria.builder().sortOrder(sortOrder).maxPiecesPerPack(40).maxWeightPerPack(500.0f).build())
                .items(Arrays.asList(
                        Item.builder().id(1001).length(6200).quantity(30).weight(9.653f).build(),
                        Item.builder().id(2001).length(7200).quantity(50).weight(11.2f).build()))
                .build();
    }

    @Test
    public void packItems_happy_path_natural() {
        // given
        PackingCommand packingCommand = createExamplePackingCommand(SortOrder.NATURAL);

        // when
        List<Pack> packs = packer.packItems(packingCommand);

        // then
        assertThat(packs).hasSize(2);
        assertThat(packs.get(0).getItems()).hasSize(2);
        assertThat(packs.get(0).getItems()).extracting(Item::getId).containsExactly(1001, 2001);
        assertThat(packs.get(0).getItems()).extracting(Item::getLength).containsExactly(6200, 7200);
        assertThat(packs.get(0).getItems()).extracting(Item::getQuantity).containsExactly(30, 10);
        assertThat(packs.get(0).getItems()).extracting(Item::getWeight).containsExactly(9.653f, 11.2f);
        assertThat(packs.get(0).getPackLength()).isEqualTo(7200);
        assertThat(packs.get(0).getWeight()).isCloseTo(401.69f, Percentage.withPercentage(10));

        assertThat(packs.get(1).getItems()).hasSize(1);
        assertThat(packs.get(1).getItems()).extracting(Item::getId).containsExactly(2001);
        assertThat(packs.get(1).getItems()).extracting(Item::getLength).containsExactly(7200);
        assertThat(packs.get(1).getItems()).extracting(Item::getQuantity).containsExactly(40);
        assertThat(packs.get(1).getItems()).extracting(Item::getWeight).containsExactly(11.2f);
        assertThat(packs.get(1).getPackLength()).isEqualTo(7200);
        assertThat(packs.get(1).getWeight()).isCloseTo(448.4f, Percentage.withPercentage(10));
    }

    @Test
    public void packItems_happy_path_long_to_short() {
        // given
        PackingCommand packingCommand = createExamplePackingCommand(SortOrder.LONG_TO_SHORT);

        // when
        List<Pack> packs = packer.packItems(packingCommand);

        // then
        assertThat(packs).hasSize(2);
        assertThat(packs.get(0).getItems()).hasSize(1);
        assertThat(packs.get(0).getItems()).extracting(Item::getId).containsExactly(2001);
        assertThat(packs.get(0).getItems()).extracting(Item::getLength).containsExactly(7200);
        assertThat(packs.get(0).getItems()).extracting(Item::getQuantity).containsExactly(40);
        assertThat(packs.get(0).getItems()).extracting(Item::getWeight).containsExactly(11.2f);
        assertThat(packs.get(0).getPackLength()).isEqualTo(7200);
        assertThat(packs.get(0).getWeight()).isCloseTo(448.0f, Percentage.withPercentage(10));

        assertThat(packs.get(1).getItems()).hasSize(2);
        assertThat(packs.get(1).getItems()).extracting(Item::getId).containsExactly(2001, 1001);
        assertThat(packs.get(1).getItems()).extracting(Item::getLength).containsExactly(7200, 6200);
        assertThat(packs.get(1).getItems()).extracting(Item::getQuantity).containsExactly(10, 30);
        assertThat(packs.get(1).getItems()).extracting(Item::getWeight).containsExactly(11.2f, 9.653f);
        assertThat(packs.get(1).getPackLength()).isEqualTo(7200);
        assertThat(packs.get(1).getWeight()).isCloseTo(401.59f, Percentage.withPercentage(10));
    }

}