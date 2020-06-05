package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@Builder
public class EtlMetadata {

    private String name;
    private List<EtlField> fields;

}
