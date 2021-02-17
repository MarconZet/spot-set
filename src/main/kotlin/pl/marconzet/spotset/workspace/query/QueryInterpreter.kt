package pl.marconzet.spotset.workspace.query

import org.springframework.stereotype.Service

@Service
class QueryInterpreter {
    fun tokenize(query: String): List<Token>{
        val clean = query.replace(" ", "").toUpperCase()
        return emptyList()
    }
}