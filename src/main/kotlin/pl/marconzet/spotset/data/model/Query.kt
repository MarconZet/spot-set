package pl.marconzet.spotset.data.model

import javax.persistence.*

@Entity
data class Query(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @ManyToOne
    val ssUser: SsUser,
    val query_text: String
)
