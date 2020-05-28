package com.example.htmlapp

import com.example.MyConstants
import com.example.MySession
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.content.PartData
import io.ktor.request.receiveMultipart
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.sessions.clear
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.slf4j.LoggerFactory

data class Session(val username: String)

fun Route.loginFlow() {
    get(Endpoints.LOGIN.url) {
        call.respondHtmlTemplate(LoginTemplate()) {
            greeting{
                +"Please login to continue"
            }
        }
    }

    get(Endpoints.HOME.url) {
        val session = call.sessions.get<MySession>()
        call.respondHtmlTemplate(HomeTemplate()) {
            greeting{
                if (session == null){
                    +"You are not logged in my friend... Please do that and then return"
                }
                else
                    +"Welcome welcome welcome! You are logged in as ${session.username}"
            }
        }
    }

    get(Endpoints.LOGOUT.url) {
        call.sessions.clear<MySession>()
        call.respondHtmlTemplate(HomeTemplate()) {

        }
    }

    post(Endpoints.DOLOGIN.url) {
        val log = LoggerFactory.getLogger("DoLogin")
        val multipart = call.receiveMultipart()
        call.request.headers.forEach { s, list ->
            log.info("key $s values $list")
        }
        var username: String =""
        var password: String =""
        while (true) {
            val part = multipart.readPart() ?: break
            when (part) {
                is PartData.FormItem -> {
                    log.info("FormItem: ${part.name} = ${part.value}")
                    if (part.name == "username")
                        username = part.value
                    if (part.name == "password")
                        password = part.value
                }
                is PartData.FileItem -> {
                    log.info("FileItem: ${part.name} -> ${part.originalFileName} of ${part.contentType}")
                }
            }
            part.dispose()
        }
        if (Security().credentialsValid(username, password)) {
            call.sessions.set(MySession(username))
            call.respondHtmlTemplate(
                HomeTemplate()
            ) {
                greeting {
                    +"You are logged in as $username and a cookie has been created"
                }
            }
        }
        else
            call.respondHtmlTemplate(LoginTemplate()){
                greeting{
                    +"Username or password was invalid... Try again."
                }
            }
    }
}
