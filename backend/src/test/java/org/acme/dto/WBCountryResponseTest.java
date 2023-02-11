package org.acme.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.ResourcesUtil;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.dto.wb.country.WBCountryResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class WBCountryResponseTest {

    ObjectMapper mapper = new ObjectMapper();
    @Test
    public void basicParsingTest() throws IOException {
        var input = Files.readString(ResourcesUtil.getFile(this.getClass(),"/responses/country_ok.json").toPath());
        var objectMapped = mapper.readValue(input, WBCountryResponse.class);
        assertEquals(6, objectMapped.getPagingInfo().getPages());
        assertEquals(50, objectMapped.getPagingInfo().getPerPage());
        assertEquals(299, objectMapped.getPagingInfo().getTotal());
        assertEquals(50, objectMapped.getData().size());
        assertEquals("AW", objectMapped.getData().get(0).getIso2Code());
    }
}
