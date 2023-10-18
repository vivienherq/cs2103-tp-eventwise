package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_EVENT_ID = new Prefix("id/");
    public static final Prefix PREFIX_PERSON = new Prefix("person/");
    public static final Prefix PREFIX_VENUE = new Prefix("venue/");
    public static final Prefix PREFIX_VENDOR = new Prefix("vendor/");
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EVENT_DESC = new Prefix("d/");
    public static final Prefix PREFIX_EVENT_DATE = new Prefix("dt/");
}
