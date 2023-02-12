package org.acme.rest.wb;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import org.acme.dto.wb.country.WBCountryResponse;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponse;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponseItem;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.*;

@Path("/country")
@RegisterRestClient(configKey = "wb-api")
public interface WBCountryClient {
    @GET
    @ClientQueryParam(name = "format", value = "json")
    RestResponse<WBCountryResponse> listCountries(@QueryParam("page") Integer page);

    @GET
    @Path("/{id}/indicator/SI.POV.DDAY")
    @ClientQueryParam(name = "format", value = "json")
    RestResponse<WBPovertyRatioResponse> getPovertyRatioData(@PathParam("id") String countryId, @QueryParam("page") Integer page);

}
