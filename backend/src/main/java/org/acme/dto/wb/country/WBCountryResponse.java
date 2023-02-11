package org.acme.dto.wb.country;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.dto.wb.WBPagingInfoDTO;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "pagingInfo", "data" })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WBCountryResponse {
    private WBPagingInfoDTO pagingInfo;
    private List<WBCountryResponseItem> data;
}
