package org.acme.dto.wb.povertyRatio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.dto.CountryResponseItem;
import org.acme.dto.PovertyRatioResponseItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WBPovertyRatioResponseItem {
    public Integer date;
    public Float value;
    public PovertyRatioResponseItem toPovertyRatioResponseItem() {
        return new PovertyRatioResponseItem(date, value);
    }
}
