package ru.xander.etl.graph.graph.etl;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alexander Shakhov
 */
@Data
@Builder
public class EtlScenario {

    private long id;
    private File file;
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
