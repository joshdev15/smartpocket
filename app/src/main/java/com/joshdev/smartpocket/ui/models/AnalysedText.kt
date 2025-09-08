package com.joshdev.smartpocket.ui.models

import android.graphics.Point

data class AnalysedBlock(
    val block: String,
    val language: String,
    val cornerPoints: Array<out Point?>?,
    val lines: List<AnalysedLine>,
    val order: Int
) {
    data class AnalysedLine(
        val line: String,
        val language: String,
        val cornerPoints: Array<out Point?>?,
        val texts: List<AnalysedText>
    ) {
        data class AnalysedText(
            val text: String,
        )

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AnalysedLine

            if (line != other.line) return false
            if (language != other.language) return false
            if (!cornerPoints.contentEquals(other.cornerPoints)) return false
            if (texts != other.texts) return false

            return true
        }

        override fun hashCode(): Int {
            var result = line.hashCode()
            result = 31 * result + language.hashCode()
            result = 31 * result + (cornerPoints?.contentHashCode() ?: 0)
            result = 31 * result + texts.hashCode()
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnalysedBlock

        if (block != other.block) return false
        if (language != other.language) return false
        if (!cornerPoints.contentEquals(other.cornerPoints)) return false
        if (lines != other.lines) return false

        return true
    }

    override fun hashCode(): Int {
        var result = block.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + (cornerPoints?.contentHashCode() ?: 0)
        result = 31 * result + lines.hashCode()
        return result
    }
}
