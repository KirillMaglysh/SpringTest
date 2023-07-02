import net.sf.cglib.proxy.Enhancer
import net.sf.cglib.proxy.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.KClass

/**
 * Place here class purpose.
 *
 * @author Kirill
 * @since 02.07.2023
 */
object DeprecatedHandlerProxyConfigurator : ProxyConfigurator {
    override fun replaceWithProxyIfNeeded(instance: Any, implClass: KClass<out Any>): Any {
        return if (implClass.java.isAnnotationPresent(Deprecated::class.java)) {
            getNeededProxyObject(implClass, instance)
        } else {
            instance
        }
    }

    private fun getNeededProxyObject(implClass: KClass<out Any>, instance: Any): Any =
        if (implClass.java.interfaces.isEmpty()) {
            getCgiLibProxyObject(implClass, instance)
        } else {
            getJavaApiProxyObject(implClass, instance)
        }

    private fun getJavaApiProxyObject(implClass: KClass<out Any>, instance: Any): Any =
        Proxy.newProxyInstance(
            implClass.java.classLoader, implClass.java.interfaces
        ) { _, method, args ->
            getInvocationHandlerLogic(method, instance, args)
        }

    private fun getCgiLibProxyObject(implClass: KClass<out Any>, instance: Any): Any =
        Enhancer.create(implClass.java, InvocationHandler { _, method, args ->
            getInvocationHandlerLogic(method, instance, args)
        })

    private fun getInvocationHandlerLogic(
        method: Method,
        instance: Any,
        args: Array<out Any>
    ): Any? {
        println("*******What are you doing? This class is deprecated******* ")
        return method.invoke(instance, *args)
    }

}