package ca.robinssoftware.mpack;

public enum Category {

    CHAT, UTILS, ECONOMY, SKRIPT, API, WORLD, MISC, MECHANICS, FUN, WEB, ADMIN;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
    
    public static Category of(String name) {
        return Category.valueOf(name.toUpperCase());
    }

}
