package pl.marconzet.spotset.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pl.marconzet.spotset.data.model.User

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun getUserBySpotifyId(spotifyId: String) : User?
}