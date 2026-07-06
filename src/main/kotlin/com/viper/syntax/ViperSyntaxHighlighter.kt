package com.viper.syntax

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class ViperSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        val KEYWORD = key("VIPER_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
        val TYPE = key("VIPER_TYPE", DefaultLanguageHighlighterColors.CLASS_NAME)
        val NUMBER = key("VIPER_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val STRING = key("VIPER_STRING", DefaultLanguageHighlighterColors.STRING)
        val LINE_COMMENT = key("VIPER_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val BLOCK_COMMENT = key("VIPER_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
        val IDENTIFIER = key("VIPER_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        val OPERATOR = key("VIPER_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN)
        val BRACES = key("VIPER_BRACES", DefaultLanguageHighlighterColors.BRACES)
        val BRACKETS = key("VIPER_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
        val PARENS = key("VIPER_PARENS", DefaultLanguageHighlighterColors.PARENTHESES)
        val COMMA = key("VIPER_COMMA", DefaultLanguageHighlighterColors.COMMA)
        val DOT = key("VIPER_DOT", DefaultLanguageHighlighterColors.DOT)

        private fun key(name: String, fallback: TextAttributesKey) =
            TextAttributesKey.createTextAttributesKey(name, fallback)

        private val EMPTY = emptyArray<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer = ViperLexer()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> = when (tokenType) {
        ViperTokenTypes.KEYWORD -> arrayOf(KEYWORD)
        ViperTokenTypes.TYPE -> arrayOf(TYPE)
        ViperTokenTypes.NUMBER -> arrayOf(NUMBER)
        ViperTokenTypes.STRING -> arrayOf(STRING)
        ViperTokenTypes.LINE_COMMENT -> arrayOf(LINE_COMMENT)
        ViperTokenTypes.BLOCK_COMMENT -> arrayOf(BLOCK_COMMENT)
        ViperTokenTypes.IDENTIFIER -> arrayOf(IDENTIFIER)
        ViperTokenTypes.OPERATOR -> arrayOf(OPERATOR)
        ViperTokenTypes.BRACE -> arrayOf(BRACES)
        ViperTokenTypes.BRACKET -> arrayOf(BRACKETS)
        ViperTokenTypes.PAREN -> arrayOf(PARENS)
        ViperTokenTypes.COMMA -> arrayOf(COMMA)
        ViperTokenTypes.DOT -> arrayOf(DOT)
        else -> EMPTY
    }
}
