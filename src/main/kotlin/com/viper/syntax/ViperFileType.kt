package com.viper.syntax

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object ViperFileType : LanguageFileType(ViperLanguage) {
    override fun getName() = "Viper"
    override fun getDescription() = "Viper verification language"
    override fun getDefaultExtension() = "vpr"
    override fun getIcon(): Icon? = null
}
