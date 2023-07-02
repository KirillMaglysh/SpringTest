import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 30.06.2023
 */
class ApplicationContext(
    val config: Config
) {
    private lateinit var factory: ObjectFactory

    private val cache = ConcurrentHashMap<KClass<out Any>, Any>()

    fun setFactory(factory: ObjectFactory) {
        this.factory = factory
    }

    fun <T : Any> getAny(type: KClass<T>): T {
        if (cache.contains(type)) {
            return cache[type]!! as T
        }

        var implClass: KClass<out T> = type
        if (type.java.isInterface) {
            implClass = config.getImplClass(type)
        }

        val instance = factory.createAndConfigureAny(implClass)
        if (implClass.objectInstance != null) {
            cache[type] = instance
        }

        return instance
    }
}