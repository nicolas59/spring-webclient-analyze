package com.nro.demo.webclientanalyze.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class DetectLabelResponse {

    public static class LabelDescription{
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Confidence")
        private BigDecimal confidence;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getConfidence() {
            return confidence;
        }

        public void setConfidence(BigDecimal confidence) {
            this.confidence = confidence;
        }

        @Override
        public String toString() {
            return "LabelDescription{\n" +
                    "name='" + name + '\'' +
                    ", confidence=" + confidence +
                    '}';
        }
    }

    @JsonProperty("LabelModelVersion")
    private String labelModelVersion;

    @JsonProperty("Labels")
    private List<LabelDescription> labels;

    public String getLabelModelVersion() {
        return labelModelVersion;
    }

    public void setLabelModelVersion(String labelModelVersion) {
        this.labelModelVersion = labelModelVersion;
    }

    public List<LabelDescription> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelDescription> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "DetectLabel{" +
                "labelModelVersion='" + labelModelVersion + '\'' +
                "\n, labels=" + labels +
                '}';
    }
}
