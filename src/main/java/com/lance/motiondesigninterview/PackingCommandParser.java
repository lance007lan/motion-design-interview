package com.lance.motiondesigninterview;


import com.lance.motiondesigninterview.domainObjects.Item;
import com.lance.motiondesigninterview.domainObjects.PackingCommand;
import com.lance.motiondesigninterview.domainObjects.PackingCriteria;
import com.lance.motiondesigninterview.domainObjects.SortOrder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class PackingCommandParser {

    public PackingCommand parseCommand(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);

        String[] packingCriteriaLine = scanner.nextLine().split(",");
        if (ArrayUtils.isEmpty(packingCriteriaLine) || packingCriteriaLine.length != 3) {
            throw new IllegalArgumentException("Incorrect number of parameters for packing criteria");
        }
        PackingCriteria packingCriteria = PackingCriteria.builder()
                .sortOrder(SortOrder.valueOf(packingCriteriaLine[0].trim()))
                .maxPiecesPerPack(validatePostiveValue(Integer.valueOf(packingCriteriaLine[1].trim())))
                .maxWeightPerPack(validatePostiveValue(Float.valueOf(packingCriteriaLine[2].trim())))
                .build();

        List<Item> items = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String itemLine = StringUtils.trimToEmpty(scanner.nextLine());
            if (StringUtils.isEmpty(itemLine)) {
                break;
            }

            String[] itemParameters = itemLine.split(",");
            if (ArrayUtils.isEmpty(itemParameters) || itemParameters.length != 4) {
                throw new IllegalArgumentException("Incorrect number of parameters for item");
            }
            items.add(Item.builder()
                    .id(validatePostiveValue(Integer.valueOf(itemParameters[0])))
                    .length(validatePostiveValue(Integer.valueOf(itemParameters[1])))
                    .quantity(validatePostiveValue(Integer.valueOf(itemParameters[2])))
                    .weight(validatePostiveValue(Float.valueOf(itemParameters[3])))
                    .build());

        }

        return PackingCommand.builder().packingCriteria(packingCriteria).items(items).build();
    }

    private <T extends Number> T validatePostiveValue(T number) {
        if (number.doubleValue() <= 0) {
            throw new IllegalArgumentException("All number type parameters should greater than 0");
        }
        return number;
    }
}
