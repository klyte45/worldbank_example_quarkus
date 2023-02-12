package org.acme.dto.wb.povertyRatio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.acme.dto.wb.WBPagingInfoDTO;
import org.acme.dto.wb.WBResponseBase;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "pagingInfo", "data" })
@SuperBuilder
@AllArgsConstructor
public class WBPovertyRatioResponse extends WBResponseBase<WBPovertyRatioResponseItem> {
}