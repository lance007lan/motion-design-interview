package com.lance.motiondesigninterview.domainObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Builder
@Getter
public class PackingCommand {
    @NonNull
    private PackingCriteria packingCriteria;
    private List<Item> items;
}
