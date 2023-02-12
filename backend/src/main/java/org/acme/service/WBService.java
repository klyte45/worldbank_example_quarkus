package org.acme.service;


import io.quarkus.cache.*;
import io.quarkus.cache.runtime.UndefinedCacheKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.CountryResponseItem;
import org.acme.dto.PovertyRatioResponseItem;
import org.acme.dto.wb.WBResponseBase;
import org.acme.dto.wb.country.WBCountryResponse;
import org.acme.dto.wb.country.WBCountryResponseItem;
import org.acme.dto.wb.povertyRatio.WBPovertyRatioResponseItem;
import org.acme.rest.wb.WBCountryClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.Cache;
import org.jboss.resteasy.reactive.RestResponse;

import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class WBService {
    @RestClient
    WBCountryClient countryClient;

    public List<CountryResponseItem> loadCountries() {
        log.debug("Getting countries info");
        return iterateOverPages(countryClient::listCountries, WBCountryResponseItem::toCountryResponseItem);
    }

    private <T, D> List<D> iterateOverPages(Function<Integer, RestResponse<? extends WBResponseBase<T>>> pageScannerFn, Function<T, D> transformFn) {
        var firstPageResponse = pageScannerFn.apply(1);
        if (firstPageResponse.getStatus() == 404) {
            throw new NotFoundException();
        } else if (firstPageResponse.getStatus() / 100 == 2) {
            var firstPage = firstPageResponse.getEntity();
            if (firstPage.getPagingInfo().getTotal() > 0) {
                var response = firstPage.getData().stream().map(transformFn).collect(Collectors.toList());
                var pages = firstPage.getPagingInfo().getPages();
                for (int i = 2; i <= pages; i++) {
                    log.debug("Getting data: page {}/{}", i, pages);
                    var page = pageScannerFn.apply(i);
                    response.addAll(page.getEntity().getData().stream().map(transformFn).collect(Collectors.toList()));
                }
                return response;
            } else {
                throw new NotFoundException();
            }
        } else {
            log.info("Error getting information data @ {} - Status code {}:\n{}", firstPageResponse.getLocation(), firstPageResponse.getStatusInfo(), firstPageResponse.readEntity(String.class));
            throw new InternalServerErrorException(String.format("The data could not be processed due an unexpected HTTP status code %s from source service.", firstPageResponse.getStatus()));
        }
    }

    public List<PovertyRatioResponseItem> getPovertyRatio(String countryId) {
        log.debug("Getting data of poverty ratio for {}", countryId);
        return iterateOverPages((x) -> countryClient.getPovertyRatioData(countryId, x), WBPovertyRatioResponseItem::toPovertyRatioResponseItem);
    }
}
