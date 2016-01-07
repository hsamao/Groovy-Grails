package rewards

class CustomerController {

    static scaffold = Customer

    def calculationsService

    def lookup() {

        /* def customerInstance = Customer.list(sort: "lastName", order: "desc", max: 5, offset: 0)
         def customerInstance = Customer.findAllByLastNameIlike("b%")
         def customerInstance = Customer.findAllByTotalPointsGreaterThanEquals(3, [sort: "totalPoints", order: "desc"])
         def customerInstance = Customer.findAllByTotalPointsBetween(2,4, [sort: "totalPoints"])*/

        def customerInstance = Customer.findAllByFirstNameIlikeAndTotalPointsGreaterThanEquals("B%", 3)
        [customerInstanceList: customerInstance]
    }

    def customerLookup(Customer lookupInstance) {

        // Query customer by phone #
        // If no result,
        // Create a new customer
        // Create welcome message
        // Add award record
        // Save customer
        // Send Welcome to kiosk
        // If customer found,
        // Calculate total points
        // Create welcome message
        // Add award record
        // Save customer
        // Send Welcome to kiosk

        def (customerInstance, welcomeMessage) = calculationsService.processCheckin(lookupInstance)
        render(view: "checkin", model: [customerInstance: customerInstance, welcomeMessage: welcomeMessage])

    }

    def checkin() {

    }

    def index() {
        params.max = 10
        [customerInstanceList: Customer.list(params), customerInstanceCount: Customer.count()]
    }

    def create() {
        [customerInstance: new Customer()]
    }

    def save(Customer customerInstance) {
        customerInstance.save()
        redirect(action: "show", id: customerInstance.id)
    }

    def show(Long id) {
        def customerInstance = Customer.get(id)
        customerInstance = calculationsService.getTotalPoints(customerInstance)
        [customerInstance: customerInstance]
    }

    def edit(Long id) {
        def customerInstance = Customer.get(id)
        [customerInstance: customerInstance]
    }

    def update(Long id) {
        def customerInstance = Customer.get(id)
        customerInstance.properties = params
        customerInstance.save()
        redirect(action: "show", id: customerInstance.id)
    }

    def delete(Long id) {
        def customerInstance = Customer.get(id)
        customerInstance.delete()
        redirect(action: "index")
    }

    def profile() {
        def customerInstance = Customer.findByPhone(params.id)
        [customerInstance: customerInstance]
    }

    def updateProfile(Customer customerInstance) {
        customerInstance.save()
        render(view: "profile", model: [customerInstance: customerInstance])
    }
}
