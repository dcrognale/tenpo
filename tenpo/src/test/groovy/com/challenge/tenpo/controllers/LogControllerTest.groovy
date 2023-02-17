package com.challenge.tenpo.controllers

import com.challenge.tenpo.services.LogService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class LogControllerTest extends Specification {
    MockMvc mockMvc
    LogService logService

    def setup() {
        logService = Mock(LogService)
        def controller = new LogController()
        controller.logService = logService
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "Should get log history from /logs endpoint, expect 200"() {
        given:
        def page = 1

        when:
        def uri = "/logs?page=$page"

        def response = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))

        then:
        1 * logService.getHistory(page)
        response.andExpect(status().isOk())
    }
}
