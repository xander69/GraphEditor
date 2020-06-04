package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Data
@Builder
public class EtlStage {

    private int phaseNum;
    private String name;
    private String displayName;
    private String description;
    private boolean enabled;
    private List<EtlParameter> parameters;

}
