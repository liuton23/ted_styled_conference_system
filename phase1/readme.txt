readme.txt

Make sure src folder is marked as sources root.
Navigate src -> Controller -> ConferenceMain.
Run ConferenceMain’s main method to start the program.
You may get an error from the Tests.java class.
If this occurs, please add the required junit dependencies to the classpath.
Do this by hovering on the red words and selecting add to classpath.
Alternatively, you can delete the Tests folder as it is not required for the program to function.

When the user runs the program, the console will say:
“Welcome to the 207th Tech Conference! You can enter 'EXIT' to exit anytime. Note that terminating the program without using the above method may not save your information."

The user will initially be given two input options.
Enter “l” or “L” to login into an existing account.
Enter “r” or “R” create and register a new account. Follow the prompts.

After creating an account, the user has to login to gain access to activities.
Once the user logs into the program, if they are just a regular attendee:
Enter “m” or “M” to access the messaging system.
Enter “e” or “E” to access the event planning system.
Enter “i” or “I” to view the events that the user currently has planned.
Enter “b” or “B” to return to the login page.

If the user is an organizer, additional options are available:
Enter “s” or “S” to access the scheduling system.
Enter “c” or “C” to create a speaker account.

If the user is a speaker, an additional option is available:
Enter “t” or “T” to view a list of upcoming events that the speaker will speak at.

Messaging system. Allows the user to message other attendees, organizers, and speakers,
Enter “m” or “M” to message other users. Follow the prompts.
Enter “v” or “V” to view messages. Follow the prompts.

After entering the MessagingUser option, if the user is an attendee:
Enter “u” or “U” if you want to message a specific user. Note that no users can send a message to an organizer.
Enter “b” or “B” if you want to return to the main Message page.

If the user is an Organizer, additional message options are:
Enter “s” or “S” if you want to message all the speakers in the system.
Enter “a” or “A” if you want to message all the attendees in the system.

If the user is a Speaker, additional message option is:
Enter “e” or “E” if you want to message to all attendees from one or multiple events.
After enter an event name (make sure exact name), you have options :
Enter "s" or "S" if you want to message to another event at the same time
Enter "c" or "C" if you do not wish to message to another event at the same time, and you will enter message after


After entering the ViewingMessage option, options are:
Enter “s” or “S” if you want to view sent messages by you.
Enter “r”  or “R” if you want to view received messages for you.
Enter “f”  or “F” if you want to view messages sent from a specific user to you.
Enter “b” or “B” if you want to return to the main Message page


Event planning system. Allows users to view all events, and sign-up/drop-out of events.
Enter “v” or “V” to view all events. Enter “T” to sort events by time, “N” to sort events by name, or “S” to sort events by speaker.
Enter “s” or “S” to sign-up for a listed event.
Enter “d” or “D” to drop-out of a listed event.
Enter “b” or “B” to return to the account activities.


The scheduling system. Allows organisers to create events and add rooms.
Enter “s” or “s” to create a new event. Follow the prompts. Note: months need to be spelled out fully using all capitol
letters. e.g. NOVEMBER
Enter “a” or “A” to Add a room. Note: rooms need to be created before they can be used.
Enter “c” or “C” to change speakers at an event. Follow the prompts.
Enter “b” or “B” to return to the account activities.
