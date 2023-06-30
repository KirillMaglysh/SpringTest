/**
 * @author Kirill
 * @since 29.06.2023
 */
class CoronaDisinfector {
    @InjectByType
    private val announcer = ObjectFactory.createObject(Announcer::class)
    @InjectByType
    private val policeman = ObjectFactory.createObject(Policeman::class)

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
