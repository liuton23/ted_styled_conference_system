readme.txt

Make sure src folder is marked as sources root.
Navigate src -> Controller -> ConferenceMain.
Run ConferenceMain’s main method to start the program.
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

If the user is an organizer, an additional option is available:
Enter “s” or “S” access the scheduling system.

If the user is a speaker, an additional option is available:
Enter “t” or “T” to view a list of upcoming events that the speaker will speak at.

Messaging system. Allows the user to message other attendees, organizers, and speakers,
Enter “m” or “M” to message other users. Follow the prompts.
Enter “v” or “V” to view messages. Follow the prompts.

After entering the MessagingUser option, you will see 5 options.
Enter “u” or “U” if you want to message a specific user. Note that every type of user can use this option but you can not send a message to an organizer. Your message will be rejected if the recipient is an organizer.
Enter “s” or “S” if you want to message all the speakers in the system. Note that only an organizer can use this option. Your message will be rejected if you are not an organizer.
Enter “a” or “A” if you want to message all the attendees in the system. Note that only an organizer can use this option. Your message will be rejected if you are not an organizer.
Enter “e” or “E” if you want to message to all attendees from one or multiple events. Note that only speakers can use this option. Your message will be rejected if you are not a speaker.
Enter “b” or “B” if you want to return to the main Message page.

After entering the ViewingMessage option, you will see 3 options
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
Enter “s” or “s” to create a new event. Follow the prompts.
Enter “a” or “A” to Add a room. Note: rooms need to be created before they can be used.
Enter “c” or “C” to change speakers at an event. Follow the prompts.
Enter “b” or “B” to return to the account activities.
