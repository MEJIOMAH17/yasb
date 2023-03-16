import org.gradle.api.Project

fun Project.name(): String {
    val projects = arrayListOf<Project>()
    var project = this
    while (project != rootProject) {
        projects.add(project)
        project = project.parent!!
    }
    return projects.asReversed().map { it.name }.joinToString("-")
}