/**
 * @author Kirill
 * @since 29.06.2023
 */
class ConsoleAnnouncer : Announcer {
    @field:InjectByType
    private lateinit var recommendator: Recommendator

    override fun announce(message: String) {
        println(message)
        recommendator.recomend()
    }
}
