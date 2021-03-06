package com.example.htmlapp

import com.example.ViewTemplate
import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import kotlinx.html.*

class LoginTemplate(val basicTemplate: ViewTemplate = ViewTemplate()) : Template<HTML> {
    val greeting = Placeholder<FlowContent>()
    override fun HTML.apply() {
        insert(basicTemplate) {

            content {
                div(classes = "mt-2") {
                    h2() {
                        +"Login page"
                    }
                    p{
                        insert(greeting)
                    }
                }
                form(
                    method = FormMethod.post,
                    encType = FormEncType.multipartFormData,
                    action = Endpoints.DOLOGIN.url
                ) {
                    div(classes = "mb-3") {
                        input(type = InputType.text, classes = "form-control", name = "username") {
                            this.placeholder = "Username goes here..."
                            this.attributes.put("aria-label", "Username")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        input(type = InputType.password, classes = "form-control", name = "password") {
                            this.placeholder = "Password goes here..."
                            this.attributes.put("aria-label", "Password")
                            this.attributes.put("aria-describedby", "basic-addon1")
                        }
                    }
                    div(classes = "mb-3") {
                        button(classes = "btn btn-primary", type = ButtonType.submit) {
                            +"Login and proceed to home"
                        }
                    }
                }
            }
        }
    }
}