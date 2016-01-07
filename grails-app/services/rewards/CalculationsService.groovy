package rewards

import grails.transaction.Transactional

@Transactional
class CalculationsService {

    def welcome(params) {
        def firstName = params.first
        def totalPoints = params.points.toInteger()
        def welcomeMessage = ""

        switch (totalPoints) {
            case 5..10:
                welcomeMessage = "Welcome back $firstName, your drink is on us!"
                break
            case 4:
                welcomeMessage = "Welcome back $firstName, your next drink is free."
                break
            case 2..3:
                welcomeMessage = "Welcome back $firstName, you now have $totalPoints points."
                break
            default:
                welcomeMessage = "Welcome $firstName. Thanks for registering."
        }

    }

    def getTotalPoints(Customer customerInstance) {

        def totalAwards = 0
        customerInstance.awards.each {
            totalAwards = totalAwards + it.points
        }
        customerInstance.totalPoints = totalAwards
        return customerInstance
    }

    def processCheckin(lookupInstance) {
        // lookup customer by phone number
        def customerInstance = Customer.findByPhone(lookupInstance.phone)

        // Set up new customer
        if (!customerInstance) {
            customerInstance = lookupInstance
            customerInstance.firstName = "Customer"
        }

        // Calculate current award points
        def totalAwards = 0
        customerInstance.awards.each {
            totalAwards = totalAwards + it.points
        }
        customerInstance.totalPoints = totalAwards

        // Create welcome message
        def welcomeMessage = ""
        switch (totalAwards) {
            case 5..10:
                welcomeMessage = "Welcome back $customerInstance.firstName, your drink is on us!"
                break
            case 4:
                welcomeMessage = "Welcome back $customerInstance.firstName, your next drink is free."
                break
            case 1..3:
                welcomeMessage = "Welcome back $customerInstance.firstName, you now have ${totalAwards + 1} points."
                break
            default:
                welcomeMessage = "Welcome $customerInstance.firstName. Thanks for registering."
                break
        }

        // Adde new award
        if (totalAwards < 5){
            customerInstance.addToAwards(new Award(awardDate: new Date(), type: "Purchase", points: 1))
        } else {
            customerInstance.addToAwards(new Award(awardDate: new Date(), type: "Reward", points: -5))
        }

        // Save customer
        customerInstance.save()

        return [customerInstance, welcomeMessage]
    }
}
