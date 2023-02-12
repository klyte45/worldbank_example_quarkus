package org.acme.rest;

import org.acme.dto.CountryResponseItem;
import org.acme.dto.PovertyRatioResponseItem;
import org.acme.service.WBService;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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

    @Operation(summary = "List all countries and regions valid to be accessed in World Bank API calls.")
    @APIResponse(responseCode = "200",
            description = "Go   t the list of all countries and regions available at World Bank API",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(
                            implementation = CountryResponseItem.class,
                            type = SchemaType.ARRAY)))
    @GET
    @Path("/country")
    public RestResponse<List<CountryResponseItem>> listCountries() {
        return RestResponse.ok(wbService.loadCountries());
    }

    @Operation(summary = "Get the available data of Poverty Ratio from a given country or region.")
    @APIResponse(responseCode = "200",
            description = "Got all data available for this country/region.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(
                            implementation = PovertyRatioResponseItem.class,
                            type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "404",
            description = "There are no data available for this country/region.")
    @GET
    @Path("/povertyRatio/{id}")
    public RestResponse<List<PovertyRatioResponseItem>> getPovertyRatio(
            @Parameter(description = "ID from country/region to search data. Use the `/country` API to list all options available.", required = true)
            @PathParam("id") String countryId){
        return RestResponse.ok(wbService.getPovertyRatio(countryId));
    }
}
