package com.viper.syntax

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType

class ViperLexer : LexerBase() {

    companion object {
        private val KEYWORDS = setOf(
            "field", "method", "function", "predicate", "domain", "axiom",
            "import", "requires", "ensures", "invariant", "var", "if", "else",
            "while", "return", "new", "null", "true", "false", "result", "old",
            "forall", "exists", "acc", "unfolding", "in", "applying", "fold",
            "unfold", "exhale", "inhale", "assert", "assume", "package", "wand",
            "label", "goto", "define", "quasihavoc", "quasihavocall", "fresh",
            "constraining", "with", "by", "let", "decreases", "none", "write",
            "wildcard", "epsilon"
        )

        private val TYPES = setOf(
            "Int", "Bool", "Ref", "Rational", "Perm", "Seq", "Set", "Multiset", "Map"
        )
    }

    private var buffer: CharSequence = ""
    private var endOffset: Int = 0
    private var currentOffset: Int = 0
    private var currentToken: IElementType? = null
    private var tokenStart: Int = 0
    private var tokenEnd: Int = 0

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.endOffset = endOffset
        this.currentOffset = startOffset
        advance()
    }

    override fun getState() = 0
    override fun getTokenType() = currentToken
    override fun getTokenStart() = tokenStart
    override fun getTokenEnd() = tokenEnd
    override fun getBufferSequence() = buffer
    override fun getBufferEnd() = endOffset

    override fun advance() {
        tokenStart = currentOffset
        if (currentOffset >= endOffset) {
            currentToken = null
            return
        }
        currentToken = readNextToken()
        tokenEnd = currentOffset
    }

    private fun peek(offset: Int = 0): Char? {
        val pos = currentOffset + offset
        return if (pos < endOffset) buffer[pos] else null
    }

    private fun readNextToken(): IElementType {
        val ch = buffer[currentOffset]

        if (ch.isWhitespace()) {
            while (currentOffset < endOffset && buffer[currentOffset].isWhitespace()) currentOffset++
            return ViperTokenTypes.WHITESPACE
        }

        // Line comment
        if (ch == '/' && peek(1) == '/') {
            while (currentOffset < endOffset && buffer[currentOffset] != '\n') currentOffset++
            return ViperTokenTypes.LINE_COMMENT
        }

        // Block comment
        if (ch == '/' && peek(1) == '*') {
            currentOffset += 2
            while (currentOffset < endOffset) {
                if (buffer[currentOffset] == '*' && peek(1) == '/') {
                    currentOffset += 2
                    break
                }
                currentOffset++
            }
            return ViperTokenTypes.BLOCK_COMMENT
        }

        if (ch.isDigit()) {
            while (currentOffset < endOffset && (buffer[currentOffset].isDigit() || buffer[currentOffset] == '.')) currentOffset++
            return ViperTokenTypes.NUMBER
        }

        if (ch.isLetter() || ch == '_' || ch == '$') {
            val start = currentOffset
            while (currentOffset < endOffset && (buffer[currentOffset].isLetterOrDigit() || buffer[currentOffset] == '_' || buffer[currentOffset] == '$')) {
                currentOffset++
            }
            val word = buffer.subSequence(start, currentOffset).toString()
            return when (word) {
                in KEYWORDS -> ViperTokenTypes.KEYWORD
                in TYPES -> ViperTokenTypes.TYPE
                else -> ViperTokenTypes.IDENTIFIER
            }
        }

        currentOffset++
        return when (ch) {
            '(' , ')' -> ViperTokenTypes.PAREN
            '[', ']' -> ViperTokenTypes.BRACKET
            '{', '}' -> ViperTokenTypes.BRACE
            ',' -> ViperTokenTypes.COMMA
            ';' -> ViperTokenTypes.SEMICOLON
            '.' -> ViperTokenTypes.DOT
            // Multi-char operators
            ':' -> { if (peek() == '=') currentOffset++; ViperTokenTypes.OPERATOR }
            '=' -> { if (peek() == '=') currentOffset++; ViperTokenTypes.OPERATOR }
            '!' -> { if (peek() == '=') currentOffset++; ViperTokenTypes.OPERATOR }
            '<' -> { if (peek() == '=' || peek() == '<') currentOffset++; ViperTokenTypes.OPERATOR }
            '>' -> { if (peek() == '=') currentOffset++; ViperTokenTypes.OPERATOR }
            '&' -> { if (peek() == '&') currentOffset++; ViperTokenTypes.OPERATOR }
            '|' -> { if (peek() == '|') currentOffset++; ViperTokenTypes.OPERATOR }
            // ==> implication
            '-' -> {
                if (peek() == '>') currentOffset++
                else if (peek() == '-' && peek(1) == '*') currentOffset += 2  // --*  magic wand
                ViperTokenTypes.OPERATOR
            }
            '+', '*', '/', '%', '?', '^', '~', '@', '#', '\\' -> ViperTokenTypes.OPERATOR
            '"' -> {
                while (currentOffset < endOffset && buffer[currentOffset] != '"') {
                    if (buffer[currentOffset] == '\\') currentOffset++
                    if (currentOffset < endOffset) currentOffset++
                }
                if (currentOffset < endOffset) currentOffset++
                ViperTokenTypes.STRING
            }
            else -> ViperTokenTypes.BAD_CHARACTER
        }
    }
}
