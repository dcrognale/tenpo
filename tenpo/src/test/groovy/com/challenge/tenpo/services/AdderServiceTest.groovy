package com.challenge.tenpo.services

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.HttpServerErrorException
import spock.lang.Specification

class AdderServiceTest extends Specification {

    private AdderService adderService

    def setup() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
        adderService = new AdderService(restTemplateBuilder)
    }

    def "Calculate values with percentage, expect ok"() {
        given: "A valid percentage"
        adderService.percentaje = 1.1

        when:
        def result = adderService.sumValues(5, 5)

        then:
        result == 11
    }

    def "Calculate values with null percentage present, expect 500"() {
        given:"A invalid percentage"
        adderService.percentaje = null

        when:
        def result = adderService.sumValues(5, 5)

        then:
        HttpServerErrorException ex = thrown()
        ex.message.contains('Percentage is not possible to calculate')
    }
}
