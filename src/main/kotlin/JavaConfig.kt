import org.reflections.Reflections
import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 29.06.2023
 */
class JavaConfig(
    packageToScan: String,
    private var ifc2ImplClass: HashMap<KClass<out Any>, KClass<out Any>>
) : Config {

    private var scanner: Reflections

    init {
        scanner = Reflections(packageToScan)
    }

    override fun <T : Any> getImplClass(ifc: KClass<T>): KClass<out T> {
        return ifc2ImplClass.computeIfAbsent(
            ifc
        ) {
            val classes = scanner.getSubTypesOf(ifc.java)
            if (classes.size != 1) {
                throw RuntimeException(ifc.qualifiedName + " has0 or more than one impl")
            }

            classes.iterator().next().kotlin
        } as KClass<out T>
    }

    override fun getScanner(): Reflections {
        return scanner
    }
}