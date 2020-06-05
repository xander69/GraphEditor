package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@Builder
public class EtlField {

    private int order;
    private String name;
    private EtlFieldType type;
    private int size;
    private int scale;

}
