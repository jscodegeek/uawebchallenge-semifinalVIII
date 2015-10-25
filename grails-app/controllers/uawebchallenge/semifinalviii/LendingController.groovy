package uawebchallenge.semifinalviii

import grails.converters.JSON

class LendingController {

    def list() {
        try {
            def list = Lending.list()
            render(status: 200, contentType: "text/json", text: [status: "success", data: list, message: ""] as JSON)
        }catch (Exception e) {
            render(status: 500, contentType: "text/json", text: [status: "error", data: "", message: "Some error happened on server!"] as JSON)

        }
    }

    def save() {

        def params = request.JSON

        if(params.userId && params.name) {
              def Lending = new Lending(userId: params.userId,
                                        name: params.name,
                                        projectLink: params.projectLink,
                                        cssStyle: params.cssStyle,
                                        page: params.page,
                                        token: new Date().getTime().encodeAsMD5().toString()).save(flush: true, failOnError: true)
            render(status: 200, contentType: "text/json", text: [status: "success", data: Lending, message: ""] as JSON)
        }else{
            render(status: 400, contentType: "text/json", text: [status: "error", data: "", message: "Not enough params!"] as JSON)
        }
    }

    def show() {
        try {
            def lending = Lending.get(params.id)
            if(lending){
                render(status: 200, contentType: "text/json", text: [status: "success", data: lending, message: ""] as JSON)
            } else {
                render(status: 404, contentType: "text/json", text: [status: "error", date: "", message: "Lending not fount by id!"] as JSON)
            }

        }catch(Exception e) {
            render(status: 500, contentType: "text/json", text: [status: "error", data: "", message: "Some internal error happened on server!"])
        }
    }

    def update() { }

    def delete() {
        try {
            def lending = Lending.get(params.id)
            if(lending){
                lending.delete(flush: true)
                render(status: 202, contentType: "text/json", text: [status: "success", data: "", message: "Lending deleted!"] as JSON)
            } else {
                render(status: 404, contentType: "text/json", text: [status: "error", data:"", message: "Lending not fount by id!"] as JSON)
            }
        }catch(Exception e) {
            render(status: 500, contentType: "text/json", text: [status: "error", data: "", message: "Some internal error happened on server!"])
        }
    }
}
