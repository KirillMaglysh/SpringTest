/**
 * @author Kirill
 * @since 30.06.2023
 */

fun main() {
    val context = Application.run("", hashMapOf(Pair(Policeman::class, PolicemanImpl::class)))
    val disinfector = context.getAny(CoronaDisinfector::class)
    disinfector.start(Room())
}
