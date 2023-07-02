import kotlin.reflect.KClass

/**
 * @author Kirill
 * @since 02.07.2023
 */
object Application {
    fun run(
        packageToScan: String, ifc2ImplClass: HashMap<KClass<out Any>, KClass<out Any>>
    ): ApplicationContext {
        val config = JavaConfig(packageToScan, ifc2ImplClass)
        val context = ApplicationContext(config)
        context.setFactory(ObjectFactory(context))
        return context
    }
}
