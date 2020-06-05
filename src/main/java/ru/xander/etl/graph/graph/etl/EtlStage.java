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
public class EtlStage {

    private int phaseNum;
    private String name;
    private String displayName;
    private String description;
    private boolean enabled;
    private List<EtlParameter> parameters;

}
