package com.typicode.jsonplaceholder.utils

class WordFrequencyUtil {

    companion object {
        fun getWordFrequency(text: String): Map<String, Int> {
            val wordCountMap = mutableMapOf<String, Int>()
            val regex = "\\b\\w+\\b".toRegex()
            regex.findAll(text).forEach { matchResult ->
                val word = matchResult.value.lowercase()
                wordCountMap[word] = wordCountMap.getOrDefault(word, 0) + 1
            }
            return wordCountMap
        }

        fun getTopNWords(wordCountMap: Map<String, Int>, n: Int): List<Pair<String, Int>> {
            return wordCountMap.entries
                .sortedByDescending { it.value }
                .take(n)
                .map { it.toPair() }
        }
    }
}
