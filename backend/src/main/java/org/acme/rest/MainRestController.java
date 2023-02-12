package org.acme.rest;

import org.acme.dto.CountryResponseItem;
import org.acme.dto.PovertyRatioResponseItem;
import org.acme.service.WBService;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MainRestController {
    @Inject
    WBService wbService;

    @GET
    @Path("/country")
    public RestResponse<List<CountryResponseItem>> listCountries() {
        return RestResponse.ok(wbService.loadCountries());
    }

    @GET
    @Path("/povertyRatio/{id}")
    public RestResponse<List<PovertyRatioResponseItem>> getPovertyRatio(@PathParam("id") String countryId){
        return RestResponse.ok(wbService.getPovertyRatio(countryId));
    }
}
