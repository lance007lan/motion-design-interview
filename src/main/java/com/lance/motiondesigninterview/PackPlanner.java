package com.lance.motiondesigninterview;

import com.lance.motiondesigninterview.domainObjects.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PackPlanner {

    public List<Pack> packItems(PackingCommand packingCommand) {

        sortItems(packingCommand);

        List<Pack> result = new ArrayList<>();
        packingCommand.getItems().forEach(item -> {
            addItemToPacks(result, item, packingCommand.getPackingCriteria());
        });
        return result;
    }

    private void addItemToPacks(List<Pack> packs, Item item, PackingCriteria packingCriteria) {
        // if there is nothing in item left, just return.
        if(item == null || item.getQuantity() <= 0) {
            return;
        }

        // check if new Pack should be created.
        boolean newPackCreated = false;
        if (packs.isEmpty() || calculateCapacityLeft(packs.get(packs.size() - 1), item, packingCriteria) <= 0) {
            packs.add(new Pack());
            newPackCreated = true;
        }

        // figure out the capacity in the last pack.
        int capacityLeft = calculateCapacityLeft(packs.get(packs.size() - 1), item, packingCriteria);

        // if cannot add anything into the new pack, there is something wrong with the inputs.
        if (capacityLeft <= 0 && newPackCreated) {
            throw new IllegalArgumentException("Invalid packing criteria, cannot add to empty pack");

        }

        int itemsToAdd = capacityLeft > item.getQuantity() ? item.getQuantity() : capacityLeft;

        // add item the last pack
        packs.get(packs.size() - 1).addItem(Item.builder().id(item.getId())
                .weight(item.getWeight()).quantity(itemsToAdd).length(item.getLength()).build());

        // look after the rest of quantity in the item.
        addItemToPacks(packs, Item.builder().id(item.getId())
                .weight(item.getWeight()).quantity(item.getQuantity() - itemsToAdd).length(item.getLength()).build(), packingCriteria);

    }

    private int calculateCapacityLeft(Pack pack, Item item, PackingCriteria packingCriteria) {
        int capacityAccordingToItemLimit = packingCriteria.getMaxPiecesPerPack() - pack.getItemNumber();
        int capacityAccordingToWeightLimit = (int) Math.ceil((packingCriteria.getMaxWeightPerPack() - pack.getWeight()) / item.getWeight());
        return Math.min(capacityAccordingToItemLimit, capacityAccordingToWeightLimit);
    }

    private void sortItems(PackingCommand packingCommand) {
        SortOrder sortOrder = packingCommand.getPackingCriteria().getSortOrder();
        if (sortOrder.equals(SortOrder.NATURAL)) {
            return;
        }
        packingCommand.getItems().sort((o1, o2) ->
                sortOrder.equals(SortOrder.SHORT_TO_LONG)
                        ? o1.getLength() - o2.getLength()
                        : o2.getLength() - o1.getLength());

    }
}
