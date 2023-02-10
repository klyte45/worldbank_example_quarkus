package org.acme.dto.wb.country;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.acme.dto.wb.WBPagingInfoDTO;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "pagingInfo", "data" })
@Data
public class WBCountryResponse {
    private WBPagingInfoDTO pagingInfo;
    private List<WBCountryResponseItem> data;
}
