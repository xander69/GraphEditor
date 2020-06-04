package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Data
@Builder
public class EtlMetadata {

    private String name;
    private List<EtlField> fields;

}
