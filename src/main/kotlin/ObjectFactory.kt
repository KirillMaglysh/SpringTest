import lombok.SneakyThrows
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
    fun <T : Any> createObject(implClass: KClass<T>): T {
        val instance = implClass.java.getDeclaredConstructor().newInstance()
        configurators.forEach { it.configure(instance, context) }

        return instance
    }
}
