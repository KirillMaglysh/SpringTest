import kotlin.reflect.KClass

/**
 * Place here class purpose.
 *
 * @author Kirill
 * @since 30.06.2023
 */
class InjectByTYpeAnnotationObjectConfiguratorImpl : ObjectConfigurator {
    override fun configure(obj: Any) {
        for (field in obj::class.java.declaredFields) {
            if (field.isAnnotationPresent(InjectByType::class.java)) {
                field.isAccessible = true
                field.set(obj, ObjectFactory.createObject(field.type as KClass<out Any>))
            }
        }
    }
}