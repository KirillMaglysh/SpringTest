import lombok.SneakyThrows
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

object InjectPropertyAnnotationObjectConfiguratorImpl : ObjectConfigurator {
    private var propertiesMap: Map<String, String>

    init {
        val path = ClassLoader.getSystemClassLoader().getResource("application.properties")!!.path
        val lines = BufferedReader(FileReader(path)).lines()
        propertiesMap = lines.map { line ->
            line.split("=").toTypedArray()
        }.collect(Collectors.toMap({ arr -> arr[0] }) { arr -> arr[1] })
    }

    @SneakyThrows
    override fun configure(obj: Any) {
        val implClass = obj::class

        for (field in implClass.java.declaredFields) {
            val annotation = field.getAnnotation(InjectProperty::class.java)
            if (annotation != null) {
                val value = if (annotation.value.isEmpty()) {
                    propertiesMap[field.name]
                } else {
                    propertiesMap[annotation.value]
                }

                field.isAccessible = true
                field.set(obj, value)
            }
        }
    }
}