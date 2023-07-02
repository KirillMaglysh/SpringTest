import javax.annotation.PostConstruct

/**
 * Place here class purpose.
 *
 * @author Kirill
 * @since 30.06.2023
 */
object RecommendatorImpl : Recommendator {
    @field:InjectProperty("whisky")
    private lateinit var alcohol: String

    @PostConstruct
    fun init() {
        println(this::class.java.name + " is initializating")
    }

    override fun recomend() {
        println("to protect from covid19 drink " + alcohol)
    }
}