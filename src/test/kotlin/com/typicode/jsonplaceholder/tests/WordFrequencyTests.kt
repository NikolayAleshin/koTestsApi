package com.typicode.jsonplaceholder.tests

import com.typicode.jsonplaceholder.utils.WordFrequencyUtil
import io.qameta.allure.*
import io.restassured.RestAssured.get
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@Epic("Posts API words")
@Feature("Word Frequency Analysis")
class WordFrequencyTests : BaseTest() {

    @Test
    @Story("Analyze Word Frequency")
    @DisplayName("Get Top 10 Words")
    @Description("This test fetches all posts and analyzes the top 10 most frequent words in the post bodies.")
    fun testTop10Words() {
        val response = get("/posts")
        val allBodies = response.jsonPath().getList<String>("body").joinToString(" ")

        step("Calculate word frequency from post bodies") {
            val wordFrequency = WordFrequencyUtil.getWordFrequency(allBodies)
            val top10Words = WordFrequencyUtil.getTopNWords(wordFrequency, 10)

            step("Save top 10 words with their frequencies to a text file") {
                val resultFile = saveTop10WordsToFile(top10Words)
                attachFileToAllure("Top 10 Words", resultFile)
            }
        }
    }

    private fun saveTop10WordsToFile(top10Words: List<Pair<String, Int>>): File {
        val resultFile = File("build/reports/top10words.txt")
        resultFile.printWriter().use { out ->
            top10Words.forEachIndexed { index, pair ->
                out.println("${index + 1}. ${pair.first} - ${pair.second}")
            }
        }
        return resultFile
    }

    private fun attachFileToAllure(name: String, file: File) {
        Allure.addAttachment(name, "text/plain", file.inputStream(), "txt")
    }
}
