package com.typicode.jsonplaceholder.tests

import io.qameta.allure.*
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Posts API")
@Feature("Post CRUD Operations")
class PostApiTests : BaseTest() {

    @Test
    @Story("Create Post")
    @DisplayName("Create a post")
    @Description("This test creates a new post and verifies the response.")
    fun testCreatePost() {
        val requestBody = """{ "title": "foo", "body": "bar", "userId": 1 }"""

        step("Send POST request to create a new post") {
            val response = createPost(requestBody)
            verifyPostResponse(response, 201, "foo", "bar", 1)
        }
    }

    @Test
    @Story("Read Post")
    @DisplayName("Read a post")
    @Description("This test reads a post by its ID and verifies the response.")
    fun testReadPost() {
        step("Send GET request to read a post") {
            val response = readPost(1)
            verifyReadPostResponse(response, 200, 1)
        }
    }

    @Test
    @Story("Update Post")
    @DisplayName("Update a post")
    @Description("This test updates a post and verifies the response.")
    fun testUpdatePost() {
        val requestBody = """{ "id": 1, "title": "foo", "body": "bar", "userId": 1 }"""

        step("Send PUT request to update a post") {
            val response = updatePost(1, requestBody)
            verifyPostResponse(response, 200, "foo", "bar", 1)
        }
    }

    @Test
    @Story("Delete Post")
    @DisplayName("Delete a post")
    @Description("This test deletes a post and verifies the response.")
    fun testDeletePost() {
        step("Send DELETE request to delete a post") {
            val response = deletePost(1)
            verifyDeleteResponse(response, 200)
        }
    }

    private fun createPost(body: String) = given()
        .contentType(ContentType.JSON)
        .body(body)
        .post("/posts")

    private fun readPost(postId: Int) = given()
        .get("/posts/$postId")

    private fun updatePost(postId: Int, body: String) = given()
        .contentType(ContentType.JSON)
        .body(body)
        .put("/posts/$postId")

    private fun deletePost(postId: Int) = given()
        .delete("/posts/$postId")

    private fun verifyPostResponse(response: io.restassured.response.Response, expectedStatusCode: Int, title: String, body: String, userId: Int) {
        response.then()
            .statusCode(expectedStatusCode)
            .body("title", equalTo(title))
            .body("body", equalTo(body))
            .body("userId", equalTo(userId))
    }

    private fun verifyReadPostResponse(response: io.restassured.response.Response, expectedStatusCode: Int, id: Int) {
        response.then()
            .statusCode(expectedStatusCode)
            .body("id", equalTo(id))
    }

    private fun verifyDeleteResponse(response: io.restassured.response.Response, expectedStatusCode: Int) {
        response.then()
            .statusCode(expectedStatusCode)
    }
}
