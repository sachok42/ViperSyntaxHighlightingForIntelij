package com.viper.syntax

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class ViperColorSettingsPage : ColorSettingsPage {

    private val descriptors = arrayOf(
        AttributesDescriptor("Keyword", ViperSyntaxHighlighter.KEYWORD),
        AttributesDescriptor("Type", ViperSyntaxHighlighter.TYPE),
        AttributesDescriptor("Number", ViperSyntaxHighlighter.NUMBER),
        AttributesDescriptor("String", ViperSyntaxHighlighter.STRING),
        AttributesDescriptor("Line comment", ViperSyntaxHighlighter.LINE_COMMENT),
        AttributesDescriptor("Block comment", ViperSyntaxHighlighter.BLOCK_COMMENT),
        AttributesDescriptor("Identifier", ViperSyntaxHighlighter.IDENTIFIER),
        AttributesDescriptor("Operator", ViperSyntaxHighlighter.OPERATOR),
        AttributesDescriptor("Braces", ViperSyntaxHighlighter.BRACES),
        AttributesDescriptor("Brackets", ViperSyntaxHighlighter.BRACKETS),
        AttributesDescriptor("Parentheses", ViperSyntaxHighlighter.PARENS),
        AttributesDescriptor("Comma", ViperSyntaxHighlighter.COMMA),
        AttributesDescriptor("Dot", ViperSyntaxHighlighter.DOT),
    )

    override fun getIcon(): Icon? = null
    override fun getHighlighter(): SyntaxHighlighter = ViperSyntaxHighlighter()
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = descriptors
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getDisplayName() = "Viper"

    override fun getDemoText() = """
// Viper verification language example
import <viper-prelude>

field val: Int
field next: Ref

/* A simple linked list predicate */
predicate list(r: Ref) {
    acc(r.val) && acc(r.next) &&
    (r.next != null ==> list(r.next))
}

function length(r: Ref): Int
    requires list(r)
{
    unfolding list(r) in
        1 + (r.next == null ? 0 : length(r.next))
}

method sumList(this: Ref) returns (res: Int)
    requires list(this)
    ensures  list(this)
    ensures  res >= 0
    decreases length(this)
{
    var x: Int := 0
    unfold list(this)
    x := this.val
    if (this.next != null) {
        var sub: Int
        sub := sumList(this.next)
        res := x + sub
    } else {
        res := x
    }
    fold list(this)
    assert res >= 0
}
    """.trimIndent()
}
