package com.typicode.jsonplaceholder.tests

import io.qameta.allure.Step
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll

abstract class BaseTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            RestAssured.baseURI = "https://jsonplaceholder.typicode.com"
        }
    }

    @Step("{description}")
    fun step(description: String, executable: () -> Unit) {
        println(description)
        executable()
    }
}
