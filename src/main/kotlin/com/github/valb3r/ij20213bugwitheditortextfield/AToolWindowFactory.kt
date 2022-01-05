package com.github.valb3r.ij20213bugwitheditortextfield

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class AToolWindowFactory: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val window = Buggy()
        val content = ContentFactory.SERVICE.getInstance().createContent(
            window.getContent(),
            "",
            false
        )
        toolWindow.contentManager.addContent(content)
    }
}
