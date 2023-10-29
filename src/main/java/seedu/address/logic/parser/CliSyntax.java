package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_EVENT_ID = new Prefix("eid/");
    public static final Prefix PREFIX_PERSON = new Prefix("pid/");
    public static final Prefix PREFIX_VENUE = new Prefix("vne/");
    public static final Prefix PREFIX_VENDOR = new Prefix("vdr/");
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EVENT_DESC = new Prefix("d/");
    public static final Prefix PREFIX_EVENT_DATE = new Prefix("dt/");
    public static final Prefix PREFIX_VENUE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_VENUE_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_VENUE_CAPACITY = new Prefix("c/");
    public static final Prefix PREFIX_RSVP_STATUS = new Prefix("s/");
}
