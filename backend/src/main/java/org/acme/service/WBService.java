package org.acme.service;


import io.quarkus.cache.*;
import io.quarkus.cache.runtime.UndefinedCacheKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.acme.dto.CountryResponseItem;
import org.acme.dto.wb.country.WBCountryResponseItem;
import org.acme.rest.wb.WBCountryClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.Cache;

import javax.inject.Singleton;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class WBService {
    @RestClient
    WBCountryClient countryClient;
    public List<CountryResponseItem> loadCountries() {
        log.info("Updating countries info");
        var firstPage = countryClient.listCountries(1);
        var response = firstPage.getData().stream().map(WBCountryResponseItem::toCountryResponseItem).collect(Collectors.toList());
        var pages = firstPage.getPagingInfo().getPages();
        for (int i = 2; i <= pages; i++) {
            log.debug("Updating country: page {}/{}", i, pages);
            var page = countryClient.listCountries(i);
            response.addAll(page.getData().stream().map(WBCountryResponseItem::toCountryResponseItem).collect(Collectors.toList()));
        }
        return response;
    }
}
