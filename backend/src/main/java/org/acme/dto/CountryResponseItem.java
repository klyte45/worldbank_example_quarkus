package org.acme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="Country response item", description="POJO that represents a country or a region in a minimal size.")
public class CountryResponseItem {
    @Schema(name = "id",
            description = "The ID of this country or region. Generally a 3 letters acronym",
            example = "BRA"
    )
    private String id;

    @Schema(name = "name",
            description = "The name of this country or region in English",
            example = "Brazil"
    )
    private String name;
}
