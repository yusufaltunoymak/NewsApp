package com.example.newsapp.data.model

import com.example.newsapp.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SummaryData {
    suspend fun getResponse(prompt: String): SummaryResponse {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
        return try {

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }
            SummaryResponse(
                summary = response.text ?: "error",
            )
        } catch (e: Exception) {
            SummaryResponse(
                summary = e.message ?: "error",
            )

        }
    }
}