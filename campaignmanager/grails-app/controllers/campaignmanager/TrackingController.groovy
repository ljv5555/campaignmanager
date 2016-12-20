package campaignmanager

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TrackingController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tracking.list(params), model:[trackingCount: Tracking.count()]
    }

    def show(Tracking tracking) {
        respond tracking
    }

    def create() {
        respond new Tracking(params)
    }

    @Transactional
    def save(Tracking tracking) {
        if (tracking == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tracking.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tracking.errors, view:'create'
            return
        }

        tracking.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tracking.label', default: 'Tracking'), tracking.id])
                redirect tracking
            }
            '*' { respond tracking, [status: CREATED] }
        }
    }

    def edit(Tracking tracking) {
        respond tracking
    }

    @Transactional
    def update(Tracking tracking) {
        if (tracking == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tracking.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tracking.errors, view:'edit'
            return
        }

        tracking.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tracking.label', default: 'Tracking'), tracking.id])
                redirect tracking
            }
            '*'{ respond tracking, [status: OK] }
        }
    }

    @Transactional
    def delete(Tracking tracking) {

        if (tracking == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tracking.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tracking.label', default: 'Tracking'), tracking.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'tracking.label', default: 'Tracking'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
