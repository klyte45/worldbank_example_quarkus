package org.acme.dto.wb.country;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.dto.CountryResponseItem;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WBCountryResponseItem {

    private String id;
    private String iso2Code;
    private String name;
    private Region region;
    private AdminRegion adminregion;
    private IncomeLevel incomeLevel;
    private LendingType lendingType;
    private String capitalCity;
    private Float longitude;
    private Float latitude;

    @Data
    public static class LendingType {
        private String id;
        private String iso2code;
        private String value;
    }

    @Data
    public static class IncomeLevel {
        private String id;
        private String iso2code;
        private String value;
    }

    @Data
    public static class AdminRegion {
        private String id;
        private String iso2code;
        private String value;
    }

    @Data
    public static class Region {
        private String id;
        private String iso2code;
        private String value;
    }

    public CountryResponseItem toCountryResponseItem(){
        return new CountryResponseItem(id, name);
    }
}
