package campaignmanager

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class RotationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Rotation.list(params), model:[rotationCount: Rotation.count()]
    }

    def show(Rotation rotation) {
        respond rotation
    }

    def create() {
        respond new Rotation(params)
    }

    @Transactional
    def save(Rotation rotation) {
        if (rotation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (rotation.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond rotation.errors, view:'create'
            return
        }

        rotation.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'rotation.label', default: 'Rotation'), rotation.id])
                redirect rotation
            }
            '*' { respond rotation, [status: CREATED] }
        }
    }

    def edit(Rotation rotation) {
        respond rotation
    }

    @Transactional
    def update(Rotation rotation) {
        if (rotation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (rotation.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond rotation.errors, view:'edit'
            return
        }

        rotation.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'rotation.label', default: 'Rotation'), rotation.id])
                redirect rotation
            }
            '*'{ respond rotation, [status: OK] }
        }
    }

    @Transactional
    def delete(Rotation rotation) {

        if (rotation == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        rotation.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'rotation.label', default: 'Rotation'), rotation.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'rotation.label', default: 'Rotation'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
