package com.github.mejiomah17.yasb

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties

class GeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val generateTables: TaskProvider<GenerateTablesTask> =
            target.tasks.register("generateTables", GenerateTablesTask::class.java)
        target.afterEvaluate {
            if (target.tasks.withType(KotlinCompile::class.java).isNotEmpty()) {
                target.tasks.withType(KotlinCompile::class.java) {
                    it.dependsOn(generateTables)
                }
                target.extensions.findByType(KotlinProjectExtension::class.java)
                    ?.sourceSets
                    ?.named("main") {
                        it.kotlin.srcDir(generateTables.get().targetDir)
                    }
                try {
                    val androidExtension = Class.forName("com.android.build.gradle.BaseExtension")
                    target.extensions.findByType(androidExtension)
                        ?.getByReflection<Any, org.gradle.api.NamedDomainObjectContainer<*>>("sourceSets")
                        ?.named("main") {
                            val kotlin = it.getByReflection<Any, Any>("kotlin")
                            kotlin.invokeFunByReflection("srcDir", generateTables.get().targetDir)
                        }
                } catch (e: NoClassDefFoundError) {
                    // android plugin does not exist in classpath
                } catch (e: ClassNotFoundException) {
                    // android plugin does not exist in classpath
                }
            }
        }
    }

    private fun <T : Any, V> T.getByReflection(property: String): V {
        val single: KProperty1<T, V> =
            this::class.declaredMemberProperties.single { it.name == property } as KProperty1<T, V>
        return single.invoke(this)
    }

    private fun <T : Any> T.invokeFunByReflection(name: String, arg: Any) {
        val single: KFunction<*> =
            this::class.declaredFunctions.single { it.name == name }
        single.call(this, arg)
    }
}
