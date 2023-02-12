package org.acme.dto.wb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "pagingInfo", "data" })
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class  WBResponseBase<T>  {
    private WBPagingInfoDTO pagingInfo;
    private List<T> data;
}