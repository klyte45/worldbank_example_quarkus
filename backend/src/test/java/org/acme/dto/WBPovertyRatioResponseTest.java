package org.acme.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.ResourcesUtil;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.dto.wb.country.WBCountryResponse;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponse;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponseItem;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class WBPovertyRatioResponseTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void basicParsingTest() throws IOException {
        var input = Files.readString(ResourcesUtil.getFile(this.getClass(), "/responses/povertyRatio_ok.json").toPath());
        var objectMapped = mapper.readValue(input, WBPovertyRatioResponse.class);
        assertEquals(2, objectMapped.getPagingInfo().getPages());
        assertEquals(50, objectMapped.getPagingInfo().getPerPage());
        assertEquals(62, objectMapped.getPagingInfo().getTotal());
        assertEquals(50, objectMapped.getData().size());
        assertNull(objectMapped.getData().get(0).getValue());
        assertEquals(2021, objectMapped.getData().get(0).getDate());
    }
}
