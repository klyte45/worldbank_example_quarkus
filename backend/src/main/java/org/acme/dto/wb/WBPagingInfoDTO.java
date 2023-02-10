package org.acme.dto.wb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WBPagingInfoDTO {
    private Float pages;
    @JsonProperty("per_page")
    private Integer perPage;
    private Float page;
    private Float total;
}
