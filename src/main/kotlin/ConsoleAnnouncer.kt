/**
 * @author Kirill
 * @since 29.06.2023
 */
class ConsoleAnnouncer : Announcer {
    private var recomendator = ObjectFactory.createObject(Recomendator::class)

    override fun announce(message: String) {
        println(message)
        recomendator.recomend()
    }
}
