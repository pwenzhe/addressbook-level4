# pwenzhe
###### \src\test\java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid favourite -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + POSTALCODE_DESC_AMY + ADDRESS_DESC_AMY + INVALID_FAVOURITE_DESC;
        assertCommandFailure(command, Favourite.MESSAGE_FAVOURITE_CONSTRAINTS);
```
