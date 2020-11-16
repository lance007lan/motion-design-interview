package com.lance.motiondesigninterview;

import com.lance.motiondesigninterview.domainObjects.PackingCommand;
import com.lance.motiondesigninterview.domainObjects.PackingCriteria;
import com.lance.motiondesigninterview.domainObjects.SortOrder;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class PackingCommandParserTest {

    private PackingCommandParser packingCommandParser = new PackingCommandParser();

    @Test
    public void parseCommand_happy_path() {
        // given

        // when
        PackingCommand packingCommand = packingCommandParser.parseCommand(this.getClass().getResourceAsStream("/happy_path.txt"));

        // then
        assertThat(packingCommand.getPackingCriteria()).isEqualTo(PackingCriteria.builder().sortOrder(SortOrder.NATURAL).maxPiecesPerPack(40).maxWeightPerPack(500.0f).build());
    }

    @Test
    public void parseCommand_when_missing_item_weight() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> packingCommandParser.parseCommand(this.getClass()
                        .getResourceAsStream("/missing_item_weight.txt")));
    }

    @Test
    public void parseCommand_when_invalid_item_weight() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> packingCommandParser.parseCommand(this.getClass()
                        .getResourceAsStream("/invalid_item_length.txt")));
    }

    @Test
    public void parseCommand_when_negative_length() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> packingCommandParser.parseCommand(this.getClass()
                        .getResourceAsStream("/negative_length.txt")));
    }

    @Test
    public void parseCommand_when_duplicated_item_ids() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> packingCommandParser.parseCommand(this.getClass()
                        .getResourceAsStream("/duplicated-item-ids.txt")));
    }

}