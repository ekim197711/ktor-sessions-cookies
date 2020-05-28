package com.example.htmlapp

import com.example.ViewTemplate
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import kotlinx.html.*

class HomeTemplate(val basicTemplate: ViewTemplate = ViewTemplate()) : Template<HTML> {
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {
            content {
                div(classes = "mt-2") {
                    h2() {
                        +"Home"
                    }
                    p{
                        insert(greeting)
                    }
                }
            }
        }
    }
}