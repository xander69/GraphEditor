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
public class EtlParameter {

    private int order;
    private String name;
    private String displayName;
    private String description;
    private EtlParameterType type;
    private String format;
    private String enumValues;
    private String enumSeparator;
    private Boolean enumMultiple;
    private String value;
    private boolean required;
    private boolean secure;

}
