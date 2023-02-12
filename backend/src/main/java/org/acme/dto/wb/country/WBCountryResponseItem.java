package org.acme.dto.wb.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.dto.CountryResponseItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WBCountryResponseItem {
    private String id;
    private String name;
    public CountryResponseItem toCountryResponseItem(){
        return new CountryResponseItem(id, name);
    }
}
