package pl.marconzet.spotset.data.model

import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    val spotifyId: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    val queryHistory: List<Query>
)
