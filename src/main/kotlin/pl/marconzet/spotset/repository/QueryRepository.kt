package pl.marconzet.spotset.repository

import org.springframework.data.repository.CrudRepository
import pl.marconzet.spotset.data.model.Query

interface QueryRepository : CrudRepository<Query, Long> {

}