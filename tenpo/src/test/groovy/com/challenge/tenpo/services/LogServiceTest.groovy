package com.challenge.tenpo.services

import com.challenge.tenpo.repositories.LogRepository
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
        res.setStatus(200)
        ContentCachingRequestWrapper reqWrapper = new ContentCachingRequestWrapper((HttpServletRequest) req);
        ContentCachingResponseWrapper respWrapper = new ContentCachingResponseWrapper((HttpServletResponse) res);

        when:
        logService.logRequest(respWrapper, reqWrapper)

        then:
        1 * logRepositoryMock.save(_)
    }

    def "When page is not present, expect 500"() {
        given:
        def page = null

        when:
        logService.getHistory(page)

        then:
        IllegalArgumentException ex = thrown()
        ex.message.contains('Page value is mandatory')
    }
}
