package org.acme.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(name="Poverty Ratio response item", description="POJO that represents a year entry at Poverty Ratio data for a country or region, minimized and anonymized.")
public class PovertyRatioResponseItem {
    @Schema(name = "year", description = "The year of this data", example = "2010")
    public Integer year;
    @Schema(name = "value", description = "The Poverty Ratio in percentage of population. If null, the data for this year is not available.", example = "3.5")
    public Float value;
}
