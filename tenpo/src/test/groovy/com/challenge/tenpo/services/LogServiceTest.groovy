package com.challenge.tenpo.services

import com.challenge.tenpo.repositories.LogRepository
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class LogServiceTest extends Specification {

    private LogService logService
    private LogRepository logRepositoryMock

    def setup() {
        logService = new LogService()
        logRepositoryMock = Mock(LogRepository)
        logService.logRepository = logRepositoryMock
    }

    def "When log request save in repository, expect OK"() {
        given: "call getHistory"
        MockHttpServletRequest req = new MockHttpServletRequest()
        MockHttpServletResponse res = new MockHttpServletResponse()

        when:
        logService.logRequest(res, req, "responseBody", "requestBody")

        then:
        1 * logRepositoryMock.save(_)
    }

    def "When getHistory with not date range, expect 500"() {
        given:
        def localFrom = null
        def localTo = null

        when:
        logService.getHistory(localFrom, localTo, 1)

        then:
        IllegalArgumentException ex = thrown()
        ex.message.contains('From and To values are mandatories.')
    }
}
