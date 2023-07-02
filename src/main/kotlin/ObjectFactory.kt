import lombok.SneakyThrows
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 29.06.2023
 */
class ObjectFactory(private val context: ApplicationContext) {
    private val configurators = ArrayList<ObjectConfigurator>()
    private val proxyConfigurators = ArrayList<ProxyConfigurator>()

    init {
        collectConfigurators(configurators, ObjectConfigurator::class)
        collectConfigurators(proxyConfigurators, ProxyConfigurator::class)
    }

    private fun <T : Any> collectConfigurators(configurators: ArrayList<T>, type: KClass<T>) {
        for (clazz in context.config.getScanner().getSubTypesOf(type.java)) {
            configurators.add(clazz.kotlin.objectInstance!!)
        }
    }

    @SneakyThrows
    fun <T : Any> createAndConfigureAny(implClass: KClass<T>): T {
        val instance = create(implClass)
        configure(instance)
        invokeInit(implClass, instance)
        return wrapWithProxyIfNeeded(instance, implClass)
    }

    private fun <T : Any> wrapWithProxyIfNeeded(instance: T, implClass: KClass<T>): T {
        var instance1 = instance
        for (proxyConfigurator in proxyConfigurators) {
            instance1 = proxyConfigurator.replaceWithProxyIfNeeded(instance1, implClass) as T
        }
        return instance1
    }

    private fun <T : Any> invokeInit(implClass: KClass<T>, instance: T) {
        for (method in implClass.java.methods) {
            if (method.isAnnotationPresent(PostConstruct::class.java)) {
                method.invoke(instance)
            }
        }
    }

    private fun <T : Any> configure(instance: T) {
        configurators.forEach { it.configure(instance, context) }
    }

    private fun <T : Any> create(implClass: KClass<T>): T = if (implClass.objectInstance == null) {
        implClass.java.getDeclaredConstructor().newInstance()
    } else {
        implClass.objectInstance!!
    }
}
