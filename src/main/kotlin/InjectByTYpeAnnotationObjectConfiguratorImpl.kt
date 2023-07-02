import kotlin.reflect.KClass

/**
 * Place here class purpose.
 *
 * @author Kirill
 * @since 30.06.2023
 */
object InjectByTYpeAnnotationObjectConfiguratorImpl : ObjectConfigurator {
    override fun configure(obj: Any, context: ApplicationContext) {
        for (field in obj::class.java.declaredFields) {
            if (field.isAnnotationPresent(InjectByType::class.java)) {
                field.isAccessible = true
                field.set(obj, context.getAny(field.type.kotlin as KClass<out Any>))
            }
        }
    }
}
