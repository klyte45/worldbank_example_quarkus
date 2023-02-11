package org.acme.rest.wb;

import io.quarkus.rest.client.reactive.ClientQueryParam;
import org.acme.dto.wb.country.WBCountryResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/country")
@RegisterRestClient(configKey = "wb-api")
public interface WBCountryClient {
    @GET
    @ClientQueryParam(name="format",value="json")
    WBCountryResponse listCountries(@QueryParam("page") Integer page);

}
