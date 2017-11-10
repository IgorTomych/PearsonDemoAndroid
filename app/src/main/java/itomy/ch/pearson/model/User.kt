package itomy.ch.pearson.model

/**
 * Created by Yegor on 11/10/17.
 */
class User(val id: String,
           val name: String,
           val username: String,
           val email: String,
           val schools: List<School>)