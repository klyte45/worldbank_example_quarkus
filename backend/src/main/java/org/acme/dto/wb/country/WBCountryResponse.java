package org.acme.dto.wb.country;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.acme.dto.wb.WBResponseBase;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "pagingInfo", "data" })
@SuperBuilder
@AllArgsConstructor
public class WBCountryResponse extends WBResponseBase<WBCountryResponseItem> {
}
