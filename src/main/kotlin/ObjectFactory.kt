import lombok.SneakyThrows
import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 29.06.2023
 */
object ObjectFactory {
    private val config: JavaConfig = JavaConfig(
        "",
        hashMapOf(Pair(Policeman::class, PolicemanImpl::class))
    )

    private val configurators = ArrayList<ObjectConfigurator>()

    init {
        for (clazz in config.scanner.getSubTypesOf(ObjectConfigurator::class.java)) {
            if (clazz.kotlin.objectInstance == null) {
                configurators.add(clazz.getDeclaredConstructor().newInstance())
            } else {
                configurators.add(clazz.kotlin.objectInstance!!)
            }
        }
    }

    @SneakyThrows
    fun <T : Any> createObject(type: KClass<T>): T {
        var implClass: KClass<out T> = type
        if (type.java.isInterface) {
            implClass = config.getImplClass(type)
        }

        val instance = implClass.java.getDeclaredConstructor().newInstance()
        configurators.forEach { it.configure(instance) }

        return instance
    }
}
