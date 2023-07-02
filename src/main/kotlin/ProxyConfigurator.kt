import kotlin.reflect.KClass

/**
 * Place here class purpose.
 *
 * @author Kirill
 * @since 02.07.2023
 */
interface ProxyConfigurator {
    fun replaceWithProxyIfNeeded(instance:Any, implClass: KClass<out Any>): Any
}