package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Data;

/**
 * @author Alexander Shakhov
 */
@Data
@Builder
public class EtlField {

    private int order;
    private String name;
    private EtlFieldType type;
    private int size;
    private int scale;

}
