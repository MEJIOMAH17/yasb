package com.github.mejiomah17.yaksb

import org.gradle.api.Action
import org.gradle.api.Project

class YasbExtension(project: Project) {
    internal val generator: YasbGeneratorExtension = YasbGeneratorExtension(project)
    fun generator(action: Action<YasbGeneratorExtension>) {
        action.execute(generator)
    }
}
