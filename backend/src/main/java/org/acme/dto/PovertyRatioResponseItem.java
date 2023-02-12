package org.acme.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PovertyRatioResponseItem {
    public Integer year;
    public Float value;
}
