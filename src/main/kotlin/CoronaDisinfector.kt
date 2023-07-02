/**
 * @author Kirill
 * @since 29.06.2023
 */
class CoronaDisinfector {
    @field:InjectByType
    private lateinit var announcer: Announcer

    @field:InjectByType
    private lateinit var policeman: Policeman

    fun start(room: Room) {
        announcer.announce("Disinfection started")
        policeman.makePeopleLeaveRoom()
        disinfect(room)
        announcer.announce("It is possible to return")
    }

    private fun disinfect(room: Room) {
        println("Corona go out")
    }
}
