package com.github.valb3r.ij20213bugwitheditortextfield.services

import com.intellij.openapi.project.Project
import com.github.valb3r.ij20213bugwitheditortextfield.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
