import lombok.SneakyThrows
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 29.06.2023
 */
class ObjectFactory(private val context: ApplicationContext) {
    private val configurators = ArrayList<ObjectConfigurator>()

    init {
        for (clazz in context.config.getScanner().getSubTypesOf(ObjectConfigurator::class.java)) {
            configurators.add(clazz.kotlin.objectInstance!!)
        }
    }

    @SneakyThrows
    fun <T : Any> createAndConfigureAny(implClass: KClass<T>): T {
        val instance = create(implClass)
        configure(instance)
        invokeInit(implClass, instance)

        return instance
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
