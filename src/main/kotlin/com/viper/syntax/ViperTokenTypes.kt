package com.viper.syntax

import com.intellij.psi.tree.IElementType

class ViperTokenType(debugName: String) : IElementType(debugName, ViperLanguage)

object ViperTokenTypes {
    val KEYWORD = ViperTokenType("KEYWORD")
    val TYPE = ViperTokenType("TYPE")
    val NUMBER = ViperTokenType("NUMBER")
    val STRING = ViperTokenType("STRING")
    val LINE_COMMENT = ViperTokenType("LINE_COMMENT")
    val BLOCK_COMMENT = ViperTokenType("BLOCK_COMMENT")
    val IDENTIFIER = ViperTokenType("IDENTIFIER")
    val OPERATOR = ViperTokenType("OPERATOR")
    val PAREN = ViperTokenType("PAREN")
    val BRACKET = ViperTokenType("BRACKET")
    val BRACE = ViperTokenType("BRACE")
    val COMMA = ViperTokenType("COMMA")
    val SEMICOLON = ViperTokenType("SEMICOLON")
    val DOT = ViperTokenType("DOT")
    val WHITESPACE = ViperTokenType("WHITESPACE")
    val BAD_CHARACTER = ViperTokenType("BAD_CHARACTER")
}
