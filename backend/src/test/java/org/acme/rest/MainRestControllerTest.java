package org.acme.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.dto.wb.WBPagingInfoDTO;
import org.acme.dto.wb.country.WBCountryResponse;
import org.acme.dto.wb.country.WBCountryResponseItem;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponse;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponseItem;
import org.acme.rest.wb.WBCountryClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;

import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

@QuarkusTest
public class MainRestControllerTest {


    @InjectMock
    @RestClient
    WBCountryClient clientMock;

    @ParameterizedTest()
    @ValueSource(ints = {1, 3, 7})
    public void country_pageCrawlerTest(int totalPages) {
        Mockito.when(clientMock.listCountries(any())).thenReturn(
                RestResponse.ok(
                        WBCountryResponse.builder()
                                .pagingInfo(WBPagingInfoDTO.builder().pages(totalPages).total(totalPages * 50).build())
                                .data(Collections.nCopies(totalPages > 0 ? 50 : 0, WBCountryResponseItem.builder().id("TST").name("Test").build()))
                                .build()
                )
        );
        given()
                .when().get("/country")
                .then()
                .statusCode(200)
                .body("$.size()", is(totalPages * 50));
        var expectedCalls = Math.max(1, totalPages);
        Mockito.verify(clientMock, Mockito.times(expectedCalls)).listCountries(any());
    }

    @ParameterizedTest
    @CsvSource({"TST,Teste"})
    public void country_mapperTest(String id, String name) {
        Mockito.when(clientMock.listCountries(any())).thenReturn(
                RestResponse.ok(
                        WBCountryResponse.builder()
                                .pagingInfo(WBPagingInfoDTO.builder().pages(1).total(1).build())
                                .data(List.of(
                                        WBCountryResponseItem.builder().id(id).name(name).build()
                                ))
                                .build()
                )
        );
        given()
                .when().get("/country")
                .then()
                .statusCode(200)
                .body("[0].id", is(id),
                        "[0].name", is(name));
    }

    @ParameterizedTest()
    @ValueSource(ints = {1, 3, 7})
    public void povertyRatio_pageCrawlerTest(int totalPages) {
        Mockito.when(clientMock.getPovertyRatioData(any(), any())).thenReturn(
                RestResponse.ok(
                        WBPovertyRatioResponse.builder()
                                .pagingInfo(WBPagingInfoDTO.builder().pages(totalPages).total(totalPages * 50).build())
                                .data(Collections.nCopies(totalPages > 0 ? 50 : 0,
                                        WBPovertyRatioResponseItem.builder().date(1999).value(null).build()
                                ))
                                .build()
                )
        );
        var expectedCalls = Math.max(1, totalPages);
        given()
                .when().get("/povertyRatio/ABC")
                .then()
                .statusCode(200)
                .body("$.size()", is(totalPages * 50));
        Mockito.verify(clientMock, Mockito.times(expectedCalls)).getPovertyRatioData(any(), any());
    }

    @ParameterizedTest
    @CsvSource({"1934,21.3"})
    public void povertyRatio_mapperTest(Integer year, Float value) {
        Mockito.when(clientMock.getPovertyRatioData(any(), any())).thenReturn(
                RestResponse.ok(
                        WBPovertyRatioResponse.builder()
                                .pagingInfo(WBPagingInfoDTO.builder().pages(1).total(1).build())
                                .data(List.of(
                                        WBPovertyRatioResponseItem.builder().date(year).value(value).build()
                                ))
                                .build()
                )
        );
        given()
                .when().get("/povertyRatio/ABC")
                .then()
                .statusCode(200)
                .body("[0].year", is(year),
                        "[0].value", is(value));
    }

    @Test
    public void povertyRatio_notFoundTest() {
        Mockito.when(clientMock.getPovertyRatioData(any(), any())).thenReturn(
                RestResponse.notFound()
        );
        given()
                .when().get("/povertyRatio/ABC")
                .then()
                .statusCode(404);
        Mockito.when(clientMock.getPovertyRatioData(any(), any())).thenReturn(
                RestResponse.ok(WBPovertyRatioResponse.builder()
                        .pagingInfo(WBPagingInfoDTO.builder().total(0).build()).build())
        );
        given()
                .when().get("/povertyRatio/ABC")
                .then()
                .statusCode(404);
    }
}
