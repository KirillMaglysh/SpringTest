/**
 * Place here class purpose.
 *
 * @author Kirill
 * @since 30.06.2023
 */
class RecomendatortImpl : Recomendator {
    @field:InjectProperty("whisky")
    private lateinit var alcohol: String

    override fun recomend() {
        println("to protect from covid19 drink " + alcohol)
    }
}