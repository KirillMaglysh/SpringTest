/**
 * @author Kirill
 * @since 30.06.2023
 */

fun main() {
    ObjectFactory.createObject(CoronaDisinfector::class).start(Room())
}
