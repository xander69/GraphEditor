package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Getter
@Setter
@Builder
public class EtlScenario {

    private long id;
    private String name;
    private String author;
    private String description;
    private String outerSystem;
    private Integer version;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<EtlParameter> startupParams;
    private List<EtlParameter> contextParams;
    private List<EtlStage> stages;
    private List<EtlMetadata> metadataList;

}
