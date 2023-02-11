package org.acme.rest;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.dto.wb.WBPagingInfoDTO;
import org.acme.dto.wb.country.WBCountryResponse;
import org.acme.dto.wb.country.WBCountryResponseItem;
import org.acme.rest.wb.WBCountryClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;

import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.List;

@QuarkusTest
public class MainRestControllerTest {


    @InjectMock
    @RestClient
    WBCountryClient clientMock;

    @ParameterizedTest()
    @ValueSource(ints = {0, 1, 3, 7})
    public void country_pageCrawlerTest(int totalPages) {
        Mockito.when(clientMock.listCountries(any())).thenReturn(WBCountryResponse.builder().pagingInfo(WBPagingInfoDTO.builder().pages(totalPages).build()).data(List.of()).build());
        given()
                .when().get("/country")
                .then()
                .statusCode(200);
        var expectedCalls = Math.max(1, totalPages);
        Mockito.verify(clientMock, Mockito.times(expectedCalls)).listCountries(any());
    }

    @ParameterizedTest
    @CsvSource( {"TST,Teste"})
    public void country_mapperTest(String id, String name){
        Mockito.when(clientMock.listCountries(any())).thenReturn(
                WBCountryResponse.builder()
                        .pagingInfo(WBPagingInfoDTO.builder().pages(1).build())
                        .data(List.of(
                                WBCountryResponseItem.builder().id(id).name(name).build()
                        ))
                        .build());
        given()
                .when().get("/country")
                .then()
                .statusCode(200)
                .body("[0].id", is(id),
                        "[0].name", is(name));

    }
}
