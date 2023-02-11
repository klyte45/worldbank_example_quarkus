package org.acme.dto.wb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WBPagingInfoDTO {
    private Integer pages;
    @JsonProperty("per_page")
    private Integer perPage;
    private Integer page;
    private Integer total;
}
