package pl.marconzet.spotset.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.marconzet.spotset.data.model.SsUser

@Repository
interface UserRepository : CrudRepository<SsUser, Long> {
    fun getUserBySpotifyId(spotifyId: String) : SsUser?
    fun getUserById(id: Long) : SsUser
}