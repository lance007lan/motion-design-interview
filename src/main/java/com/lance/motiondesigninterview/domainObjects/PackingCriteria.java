package com.lance.motiondesigninterview.domainObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Builder
@Getter
@Value
public class PackingCriteria {
    @NonNull
    private SortOrder sortOrder;
    private int maxPiecesPerPack;
    private float maxWeightPerPack;

}
