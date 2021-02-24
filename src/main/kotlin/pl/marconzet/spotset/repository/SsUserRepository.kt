package pl.marconzet.spotset.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.marconzet.spotset.data.model.SsUser

@Repository
interface SsUserRepository : CrudRepository<SsUser, Long> {
    fun getSsUserBySpotifyId(spotifyId: String): SsUser?
    fun getSsUserById(id: Long): SsUser
}