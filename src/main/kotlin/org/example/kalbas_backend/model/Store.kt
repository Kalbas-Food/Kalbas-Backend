@Entity
@Table(name = "stores")
class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // Replaced ownerId with a proper @ManyToOne relationship
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    @field:NotNull(message = "Owner cannot be null")
    var owner: User,

    @Column(name = "name", length = 255, nullable = false)
    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(max = 255, message = "Name must not exceed 255 characters")
    var name: String,

    @Column(name = "description", length = 1000)
    @field:Size(max = 1000, message = "Description must not exceed 1000 characters")
    var description: String? = null,

    @Column(name = "address", length = 500, nullable = false)
    @field:NotBlank(message = "Address cannot be blank")
    @field:Size(max = 500, message = "Address must not exceed 500 characters")
    var address: String,

    @Column(name = "phone_number", length = 25)
    @field:Pattern(
        regexp = "\\+?[0-9. ()-]{7,25}",
        message = "Phone number is invalid"
    )
    var phoneNumber: String? = null,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "is_open", nullable = false)
    var isOpen: Boolean = true,

    @Column(name = "opening_hours", nullable = false)
    @field:NotNull(message = "Opening hours cannot be null")
    var openingHours: LocalTime = LocalTime.of(8, 0),

    @Column(name = "closing_hours", nullable = false)
    @field:NotNull(message = "Closing hours cannot be null")
    var closingHours: LocalTime = LocalTime.of(22, 0),

    @Column(name = "icon_image_url", length = 2083)
    @field:Size(max = 2083, message = "Icon image URL must not exceed 2083 characters")
    var iconImageUrl: String? = null,

    @Column(name = "cover_image_url", length = 2083)
    @field:Size(max = 2083, message = "Cover image URL must not exceed 2083 characters")
    var coverImageUrl: String? = null
) {
    // JPA requires a no-arg constructor. Kotlin data classes can get this automatically
    // or you can define it. If this is not a data class, this constructor is needed.
    // However, with default values in the primary constructor, this might not be strictly necessary
    // depending on the JPA provider plugin, but it's safe to have.
    constructor() : this(
        id = null,
        owner = User(), // Note: This might need careful handling
        name = "",
        address = ""
    )
}