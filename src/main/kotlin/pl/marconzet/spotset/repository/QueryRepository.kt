package pl.marconzet.spotset.repository

import org.springframework.data.repository.CrudRepository
import pl.marconzet.spotset.data.model.Query
import pl.marconzet.spotset.data.model.User

interface QueryRepository : CrudRepository<Query, Long> {

}