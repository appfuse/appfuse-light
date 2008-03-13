class User {
    String firstName
    String lastName
    Date birthday

    static constraints = {
        firstName()
        lastName(blank:false)
        birthday()
    }

}