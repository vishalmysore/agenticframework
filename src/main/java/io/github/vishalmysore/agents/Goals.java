package io.github.vishalmysore.agents;

/**
 * A list of goals that an AI agent can have. These goals are used to determine the type of
 * instead of enup you can store goals in a database and fetch them from there or you can store them in a file and read them from there
 */
public enum Goals {
    CUSTOMER_SUPPORT_HELP("Ask Customer Support for help"),
    RESERVE_RESTAURANT("Reserve a table at a restaurant"),
    BOOK_AIRLINE_TICKET("Book an airline ticket"),
    CAR_SERVICE("Book a car service"),
    BOOK_HOTEL("Book a hotel"),
    BOOK_TRAIN_TICKET("Book a train ticket"),
    BOOK_BUS_TICKET("Book a bus ticket"),
    BOOK_MOVIE_TICKET("Book a movie ticket"),
    BOOK_CONCERT_TICKET("Book a concert ticket");

    private final String description;

    Goals(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
