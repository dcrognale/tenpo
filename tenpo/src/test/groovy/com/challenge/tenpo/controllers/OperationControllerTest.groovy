package com.challenge.tenpo.controllers

import com.challenge.tenpo.services.AdderService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.util.NestedServletException
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class OperationControllerTest extends Specification {

    MockMvc mockMvc
    AdderService adderService

    def setup() {
        adderService = Mock(AdderService)
        def controller = new OperationController()
        controller.adderService = adderService
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "Should get operation from /operation endpoint, expect 200"() {
        given:
        def value1 = 5
        def value2 = 5

        when:
        def uri = "/operation?value1=$value1&value2=$value2"

        def response = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))

        then:
        1 * adderService.sumValues(value1, value2) >> 10
        response.andExpect(status().isOk())
    }

    def "Should validate negative values, expect exception"() {
        given:
        def value1 = -5
        def value2 = 5

        when:
        def uri = "/operation?value1=$value1&value2=$value2"

        mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))

        then:
        NestedServletException ex = thrown()
        ex.message.contains('Value must be positive')
    }

    def "When superate rate limit, expect 429"() {
        given:
        def value1 = 5
        def value2 = 5

        when:
        def uri = "/operation?value1=$value1&value2=$value2"

        def response = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
        def response2 = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
        def response3 = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
        def response4 = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))

        then:
        3 * adderService.sumValues(value1, value2) >> 10
        response.andExpect(status().isOk())
        response2.andExpect(status().isOk())
        response3.andExpect(status().isOk())
        response4.andExpect(status().isTooManyRequests())
    }
}
