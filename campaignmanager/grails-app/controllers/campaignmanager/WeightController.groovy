package campaignmanager

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WeightController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Weight.list(params), model:[weightCount: Weight.count()]
    }

    def show(Weight weight) {
        respond weight
    }

    def create() {
        respond new Weight(params)
    }

    @Transactional
    def save(Weight weight) {
        if (weight == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (weight.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond weight.errors, view:'create'
            return
        }

        weight.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'weight.label', default: 'Weight'), weight.id])
                redirect weight
            }
            '*' { respond weight, [status: CREATED] }
        }
    }

    def edit(Weight weight) {
        respond weight
    }

    @Transactional
    def update(Weight weight) {
        if (weight == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (weight.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond weight.errors, view:'edit'
            return
        }

        weight.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'weight.label', default: 'Weight'), weight.id])
                redirect weight
            }
            '*'{ respond weight, [status: OK] }
        }
    }

    @Transactional
    def delete(Weight weight) {

        if (weight == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        weight.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'weight.label', default: 'Weight'), weight.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'weight.label', default: 'Weight'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
