---
layout: page
title: User Guide
---

**EventWise** provides a platform that allows all event-specific contacts, including management, attendees, vendors, and venue details to be consolidated in one place. It allows event planners to easily track and access their crucial information in a single app, simplifying event coordination and communication for various kinds of events.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `EventWise.jar` from TBA.

1. Copy the file to the folder you want to use as the _home folder_ for EventWise.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar EventWise.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `event n/PE Dry Run d/Dry run for CS2103 PE from/04-11-2024 to/05-11-2024`
   * `viewEvent eid/1`

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------
## User Interface Components
This section defines and explains the components used in EventWise's UI. 
![EventWise UI Components](images/UiComponents.png)
### 1. Main List
The Main List serves as the primary view to display a range of data types within the application which includes events, persons, vendors and venues. It provides users with an organized view of various items and facilitates interaction with the displayed content.

### 2. Event Details
The Event Details component provides users with in-depth information about a specific event or item from the Main List

#### 2a. Person List

#### 2b. Vendor List

### 3. Command Result

### 4. Command Input
--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

## Guest Features

### Adding a person: `add`

Adds a person to EventWise.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in EventWise.

Format: `list`

### Editing a person : `edit`

Edits an existing person in EventWise.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the persons list in EventWise.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in EventWise.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

## Event Features

### Create Event: `event`

This feature creates a new event object with the event name, description from date, to date and note and is stored into the event list.

Format: `event n/NAME d/DESC from/DT to/DT [no/NOTE]`

Examples:
* `event n/FSC 2023 d/Freshman Social Camp 2023 from/10-12-2023 to/11-12-2023`
* `event n/FOC 2023 d/Freshman Orientation Camp 2023 from/04-12-2023 to/05-12-2023`

**Expected Command Result**
```
Event 1: FSC 2023 has been successfully added.
```
```
Event 2: FOC 2023 has been successfully added.
```

**Invalid Command Results**
```
Create Event Failed: Event name cannot be empty.
```
```
Create Event Failed: Event description cannot be empty.
```
```
Create Event Failed: Event from date has to be in DD-MM-YYYY format.
```
```
Create Event Failed: Event to date has after from date.
```

### Add Event Details: `addEventDetails`

Adds event details such as venue, guests and vendors to a specified event.

Format: `addEventDetails eid/EVENT_ID [pid/INDEX] [vne/VENUE_ID] [vdr/VENDOR_ID]`

**What each optional field does for a specified event**
* `[person/INDEX]`: Adds the person at the specified `INDEX` as a guest of the event.
* `[venue/VENUE_ID]`: Sets the venue at the specified `VENUE_ID` as the venue of the event.
* `[vendor/VENDOR_ID]`: Adds the vendor at the specified `VENDOR_ID` as part of the event. (coming in v1.3)

**Command Behavior**

* Adds details such as venue, guests and vendors for the event at the specified `EVENT_ID`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* The optional fields `[person/INDEX]` and `[vendor/VENDOR_ID]` can be repeated more than once to add multiple people or vendors.

Examples:
* `viewEvent 3` followed by `addEventDetails 3 person/2` adds the 2nd person in the person list to 3rd event in the event list
* `viewEvent 3` followed by `addEventDetails 3 person/3 venue/2` adds the 3rd person in the person list and sets the 2nd venue in the venue list to 3rd event in the event list

**Expected Command Result**
```
Person 2: Bernice Yu has been successfully added to Event 3: FOC
```
```
Person 3: Charlotte Oliverio has been successfully added to Event 3: FOC
Venue 2: MPSH1 has beeen successfully set as the venue for Event 3: FOC
```

**Invalid Command Results**
```
Add Event Details Failed: Invalid Event ID.
```
```
Add Event Details Failed: Event ID does not exist.
```
```
Add Event Details Failed: Person does not exist.
```
```
Add Event Details Failed: Venue does not exist.
```

### View a list of Events: `viewEvents`

View all the events in a list.

**Expected Command Result**
![Ui](images/ViewEventsUI.png)

### View Event Details: `viewEvent`

View details for a specified event.

Format: `viewEvent eid/EVENT_ID`

**Event details to be displayed**
- Event Name
- Description
- Date / Time
- Venue Name
- Guest List
- Vendor List

* Displays the details for the event at the specified `EVENT_ID` from the event list.
* The Event ID refers to the index number shown in the displayed event list.
* The Event ID **must be a positive integer** 1, 2, 3, …​

Examples:
* `viewEvents` followed by `viewEvent 3` shows the details of the 3rd event in the event list.

**Expected Command Result**
![Expected Result UI](images/view-event/result.png)

**Invalid Command Results**

Non Integer Index
![Ui](images/view-event/non_integer_index.png)

Invalid Index
![](images/view-event/invalid_index.png)

### Edit Event: `editEvent`

This feature allows users to edit event details.

Format: `editEvent eid/ID [n/NAME] [d/DESC] [from/DT] [to/DT]`

**Command Behavior**
* At least one of the optional fields must be provided.

Examples:
* `editEvent eid/1 n/FSC 2024`
* `editEvent eid/1 d/Freshman Orientation Camp 2024`

**Expected Command Result**
```
Event 1: FSC 2023 - Name changed to FSC 2024.
```
```
Event 1: FSC 2024 - Description changed to Freshman Orientation Camp 2024.
```

**Invalid Command Results**
```
Edit Event Failed: Event ID does not exist.
```
```
Edit Event Failed: No parameters provided.
```

### Delete Event : `deleteEvent`

Deletes the specified event.

Format: `deleteEvent eid/EVENT_ID`

* Deletes the event at the specified `EVENT_ID` from the event list.
* The Event ID refers to the index number shown in the displayed event list.
* The Event ID **must be a positive integer** 1, 2, 3, …​

Examples:
* `viewEvents` followed by `deleteEvent eid/2` deletes the 2nd event in the event list.

**Expected Command Result**
```
Event 2: FOC, Freshman Orientation Camp has been successfully deleted
```

**Invalid Command Results**
```
Delete Event Failed: Invalid Event ID.
```
Delete Event Failed: Event ID does not exist.

### RSVP : `rsvp`

Update RSVP status of a person for a specific event.

Format: `rsvp eid/EVENT_ID pid/PERSON_ID s/STATUS`

* Set the RSVP status of the specified `EVENT_ID` and `PERSON_ID` to the new RSVP status.
* The Event ID refers to the index number shown in the displayed event list.
* The Person ID refers to the index number shown in the displayed person list.

Examples:
* `rsvp eid/1 pid/1 s/CC`
* `rsvp eid/2 pid/2 s/CCC`

**Expected Command Result**
```
RSVP status has been updated: FSC 2023, John Doe, Confirm Coming
```

**Invalid Command Results**
```
Event or Person does not exist!
```
```
Value of RSVP Status can only be CC, CCC or TBC.
```
```
John Doe 2 has not been added to FSC 2023!
```

### Find Event: `findEvent`

Finds event whose names contain any of the given keywords.

Format: `findEvent KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `caReer` will match `Career`
* The order of the keywords does not matter. e.g. `Sports Day` will match `Day Sports`
* Only the event name is searched.
* Only full words will be matched e.g. `Sport` will not match `Sports`
* Events matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Fair Day` will return `Career Fair`, `Sports Day`

Examples:
* `findEvent Fair` returns `Career Fair` and `Student Life Fair`
* `findEvent supernova lifeHack` returns `Supernova`, `LifeHack`<br>

![result for 'findEvent Supernova LifeHack'](images/find-event/result.png)

### Remove Person From Event: `removePerson`

Removes a person from a specified event

Format: `removePerson eid/EVENT_ID pid/PERSON_INDEX`
- The Event ID refers to the index number displayed in Main List
  - To view all events, type the `viewEvents` command.
- The Person ID refers to the index number displayed in the Persons List inside the event details
  - Note: 

### Remove Vendor From Event: `removeVendor`


## Venue Features

### Create Venue: `venue`

This feature creates a new venue object with the venue name, address, capacity, and is stored into the venue list.

Format: `venue n/NAME a/ADDRESS c/CAPACITY`

Examples:
* `venue n/LT 27 a/Lower Kent Ridge Road c/400`

**Expected Command Result**
```
Venue 1: LT27, Lower Kent Ridge Road, Capacity: 400 has been successfully added.
```

**Invalid Command Results**
```
Create Venue Failed: Venue name cannot be empty.
```
```
Create Venue Failed: Venue address cannot be empty.
```
```
Create Venue Failed: Venue capacity cannot be empty.
```
```
Create Venue Failed: Invalid capacity value.
```

### View a list of Venues: `viewVenues` 

View all the venues in a list.

**Expected Command Result**

### Edit Venue: `editVenue`

This feature allows users to edit venue details.

Format: `editVenue vne/VENUE_ID [n/NAME] [a/ADDRESS] [c/CAPACITY]`

**Command Behavior**
* At least one of the optional fields must be provided.

Examples:
* `editVenue vne/1 n/MPSH 1`
* `editVenue vne/1 a/5 Sports Drive 2, Singapore 117508`

**Expected Command Result**
```
Edited Venue: MPSH 1; Address: sports drive; Capacity: 200
```
```
Edited Venue: MPSH 1; Address: 5 Sports Drive 2, Singapore 117508; Capacity: 200
```

### Delete Venue : `deleteVenue`

Deletes the specified venue.

Format: `deleteVenue vne/VENUE_ID`

* Deletes the venue at the specified `VENUE_ID` from the venue list.
* Venue ID refers to the index number shown in the displayed venue list using `viewVenues`.
* Venue ID **must be a positive integer** 1, 2, 3, …​

Examples:
* `viewVenues` followed by `deleteVenue vne/2` deletes the 2nd venue in the venue list.

**Expected Command Result**
```
Deleted Venue 1: MPSH 1; Address: 5 Sports Drive 2, Singapore 117508; Capacity: 200
```

**Invalid Command Results**
```
The venue index provided is invalid
```


## Vendor Features

### Create Vendor: `vendor`

This feature creates a new vendor object with the vendor name, phone, email, and is stored into the vendor list.

Format: `vendor n/NAME p/PHONE e/EMAIL`

Examples:
* `vendor n/SUN Catering p/64647788 e/catering@sun.com`

**Expected Command Result**
```
New vendor added: SUN Catering; Phone: 64647788; Email: catering@sun.com
```

**Invalid Command Results**
```

```


### View a list of Vendors: `viewVendors`

View all the vendors in a list.

**Expected Command Result**

**Invalid Command Results**
```

```


### Edit Vendor: `editVendor`

This feature allows users to edit vendor details.

Format: `editVendor vdr/VENDOR_ID [n/NAME] [p/PHONE] [e/EMAIL]`

**Command Behavior**
* At least one of the optional fields must be provided.

Examples:
* `editVendor vdr/1 n/SUN Shuttle`
* `editVendor vdr/1 e/shuttle@sun.com`

**Expected Command Result**
```
Edited Vendor: SUN Shuttle; Phone: 80008000; Email: vendor1@gmail.com
```
```
Edited Vendor: SUN Shuttle; Phone: 80008000; Email: shuttle@sun.com
```

**Invalid Command Results**
```

```

### Delete Vendor : `deleteVendor`

Deletes the specified vendor.

Format: `deleteVendor vdr/VENDOR_ID`

* Deletes the vendor at the specified `VENDOR_ID` from the vendor list.
* Vendor ID refers to the index number shown in the displayed vendor list using `viewVendors`.
* Vendor ID **must be a positive integer** 1, 2, 3, …​

Examples:
* `viewVendors` followed by `deleteVendor vdr/2` deletes the 2nd vendor in the vendor list.

**Expected Command Result**
```
Deleted Vendor: vendor food; Phone: 90909090; Email: hihi@gmail.com
```

**Invalid Command Results**
```

```

## General Features

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Clearing all entries : `clear`

Clears all entries from EventWise.

Format: `clear`

### Clearing all event entries : `clearEvents`

Clears all event entries from EventWise.

Format: `clearEvents`

### Clearing all guest entries : `clearGuests`

Clears all guest entries from EventWise.

Format: `clearGuests`

### Clearing all venue entries : `clearVenues`

Clears all venues from EventWise.

Format: `clearVenues`

### Clearing all vendor entries : `clearVendors`

Clears all vendors from EventWise.

Format: `clearVendors`

### Saving the data

EventWise data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

EventWise data are saved automatically as a JSON file `[JAR file location]/data/eventwise.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary
### Guest Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`

### Event Command summary

Action | Format, Examples
--------|------------------
**Add Event** | `event n/NAME d/DESC from/DATE to/DATE` <br> e.g., `event n/FSC 2023 d/Freshman Social Camp 2023 from/12-12-2023 to/13-12-2023`
**Add Event Details** | `addEventDetails eid/EVENT_ID [person/INDEX] [venue/VENUE_ID] [vendor/VENDOR_ID]` <br> e.g., `addEventDetails eid/3 person/2`
**Delete Event** | `deleteEvent eid/EVENT_ID` <br> e.g., `deleteEvent eid/1`
**Edit Event** | `editEvent eid/ID [n/NAME] [d/DESC] [from/DATE] [to/DATE]` <br> e.g., `editEvent eid/1 d/Freshman Orientation Camp 2024`
**View All Events** | `viewEvents`
**View Event** | `viewEvent eid/ID` <br> e.g., `viewEvent eid/1`
**RSVP** | `rsvp eid/EVENT_ID pid/PERSON_ID s/STATUS` <br> e.g., `rsvp eid/1 pid/1 s/CC`

### Vendor Command summary

Action | Format, Examples
--------|------------------
**Add Vendor** | `vendor n/NAME p/PHONE e/EMAIL` <br> e.g., `vendor n/SUN Catering p/64646767 e/catering@sun.com`
**View Vendors** | `viewVendors`
**Edit Vendor** | `editVendor vdr/VENDOR_ID [n/NAME] [p/PHONE] [e/EMAIL]` <br> e.g., `editVendor vdr/1 n/SUN Catering`
**Delete Vendor** | `deleteVendor vdr/VENDOR_ID` <br> e.g., `deleteVendor vdr/1`

### Venue Command summary

Action | Format, Examples
--------|------------------
**Add Venue** | `venue n/NAME a/ADDRESS c/CAPACITY` <br> e.g., `venue n/MPSH 1 a/5 Sports Drive c/300`
**View Venues** | `viewVenues`
**Edit Venue** | `editVenue vne/VENUE_ID [n/NAME] [a/ADDRESS] [c/CAPACITY]` <br> e.g., `editVenue vne/1 n/MPSH 2`
**Delete Venue** | `deleteVenue vne/VENUE_ID` <br> e.g., `deleteVenue vne/1`

### General Command summary

Action | Format, Examples
--------|------------------
**Clear All** | `clear`
**Clear Events** | `clearEvents`
**Clear Guests** | `clearGuests`
**Clear Venues** | `clearVenues`
**Clear Vendors** | `clearVendors`
**Help** | `help`
