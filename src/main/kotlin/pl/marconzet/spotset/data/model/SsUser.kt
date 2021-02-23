package pl.marconzet.spotset.data.model

import javax.persistence.*

@Entity
data class SsUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    val spotifyId: String,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "ssUser")
    val queryHistory: List<Query>
)
