package pl.marconzet.spotset.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class WrongPrincipalException : RuntimeException("Wrong principal found, needs to be spotify user")