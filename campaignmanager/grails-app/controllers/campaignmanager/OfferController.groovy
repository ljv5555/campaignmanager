package campaignmanager

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OfferController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Offer.list(params), model:[offerCount: Offer.count()]
    }

    def show(Offer offer) {
        respond offer
    }

    def create() {
        respond new Offer(params)
    }

    @Transactional
    def save(Offer offer) {
        if (offer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (offer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond offer.errors, view:'create'
            return
        }

        offer.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'offer.label', default: 'Offer'), offer.id])
                redirect offer
            }
            '*' { respond offer, [status: CREATED] }
        }
    }

    def edit(Offer offer) {
        respond offer
    }

    @Transactional
    def update(Offer offer) {
        if (offer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (offer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond offer.errors, view:'edit'
            return
        }

        offer.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'offer.label', default: 'Offer'), offer.id])
                redirect offer
            }
            '*'{ respond offer, [status: OK] }
        }
    }

    @Transactional
    def delete(Offer offer) {

        if (offer == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        offer.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'offer.label', default: 'Offer'), offer.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'offer.label', default: 'Offer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
