package bg.sofia.fmi.mjt.library.commands.enums;

public enum RequestType {
    ADD("add"),
    REMOVE("remove"),
    SEARCH("search"),
    EDIT("edit"),
    RATE("rate"),
    LOGIN("logIn"),
    VIEWALLBOOKS("VIEW ALL BOOKS"),
    REGISTER("register");

    private final String value;

    RequestType(String requestType) {
        this.value = requestType;
    }

    public String getValue() {
        return value;
    }
}
