package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENUE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventDetailsCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearEventsCommand;
import seedu.address.logic.commands.ClearGuestsCommand;
import seedu.address.logic.commands.ClearVendorsCommand;
import seedu.address.logic.commands.ClearVenuesCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteVendorCommand;
import seedu.address.logic.commands.DeleteVenueCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ViewEventCommand;
import seedu.address.logic.commands.ViewEventsCommand;
import seedu.address.logic.commands.ViewVendorsCommand;
import seedu.address.logic.commands.ViewVenuesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addEventDetails() throws Exception {
        assertTrue(parser.parseCommand(AddEventDetailsCommand.COMMAND_WORD
                + " " + PREFIX_EVENT_ID + "1 " + PREFIX_PERSON + "1") instanceof AddEventDetailsCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearEvents() throws Exception {
        assertTrue(parser.parseCommand(ClearEventsCommand.COMMAND_WORD) instanceof ClearEventsCommand);
    }

    @Test
    public void parseCommand_clearGuests() throws Exception {
        assertTrue(parser.parseCommand(ClearGuestsCommand.COMMAND_WORD) instanceof ClearGuestsCommand);
    }

    @Test
    public void parseCommand_clearVendors() throws Exception {
        assertTrue(parser.parseCommand(ClearVendorsCommand.COMMAND_WORD) instanceof ClearVendorsCommand);
    }

    @Test
    public void parseCommand_clearVenues() throws Exception {
        assertTrue(parser.parseCommand(ClearVenuesCommand.COMMAND_WORD) instanceof ClearVenuesCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + PREFIX_EVENT_ID + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_deleteVendor() throws Exception {
        DeleteVendorCommand command = (DeleteVendorCommand) parser.parseCommand(
                DeleteVendorCommand.COMMAND_WORD + " " + PREFIX_VENDOR + INDEX_FIRST_VENDOR.getOneBased());
        assertEquals(new DeleteVendorCommand(INDEX_FIRST_VENDOR), command);
    }

    @Test
    public void parseCommand_deleteVenue() throws Exception {
        DeleteVenueCommand command = (DeleteVenueCommand) parser.parseCommand(
                DeleteVenueCommand.COMMAND_WORD + " " + PREFIX_VENUE + INDEX_FIRST_VENUE.getOneBased());
        assertEquals(new DeleteVenueCommand(INDEX_FIRST_VENUE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        List<String> keywords = Arrays.asList("fair", "science", "life");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(
                new seedu.address.model.event.NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_viewEvent() throws Exception {
        assertTrue(parser.parseCommand(ViewEventCommand.COMMAND_WORD
                + " " + PREFIX_EVENT_ID + "1") instanceof ViewEventCommand);
    }

    @Test
    public void parseCommand_viewEvents() throws Exception {
        assertTrue(parser.parseCommand(ViewEventsCommand.COMMAND_WORD) instanceof ViewEventsCommand);
    }

    @Test
    public void parseCommand_viewVenues() throws Exception {
        assertTrue(parser.parseCommand(ViewVendorsCommand.COMMAND_WORD) instanceof ViewVendorsCommand);
    }

    @Test
    public void parseCommand_viewVendors() throws Exception {
        assertTrue(parser.parseCommand(ViewVenuesCommand.COMMAND_WORD) instanceof ViewVenuesCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
