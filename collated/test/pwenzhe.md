# pwenzhe
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid favourite
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_FAVOURITE_DESC + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                Favourite.MESSAGE_FAVOURITE_CONSTRAINTS);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid favourite -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + POSTALCODE_DESC_AMY + ADDRESS_DESC_AMY + INVALID_FAVOURITE_DESC;
        assertCommandFailure(command, Favourite.MESSAGE_FAVOURITE_CONSTRAINTS);
```
