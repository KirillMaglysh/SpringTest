import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 29.06.2023
 */
interface Config {
    fun <T : Any> getImplClass(ifc: KClass<T>): KClass<out T>
}