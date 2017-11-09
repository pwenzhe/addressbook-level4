# pwenzhe
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // invalid favourite
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_FAVOURITE_DESC + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND,
                Favourite.MESSAGE_FAVOURITE_CONSTRAINTS);
```
###### \java\seedu\address\model\person\PersonContainsKeywordsPredicateTest.java
``` java
        // Name and phone keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "12345"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345").build()));

        // Address keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withAddress("Main Street").build()));

        // Email keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withEmail("alice@email.com").build()));

        // Postal code keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("123456"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPostalCode("123456").build()));

        // Tag keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends").build()));

        // Favourite keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("favourite"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withFavourite("yes").build()));
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid favourite -> rejected. */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_AMY + EMAIL_DESC_AMY
                + POSTALCODE_DESC_AMY + ADDRESS_DESC_AMY + INVALID_FAVOURITE_DESC;
        assertCommandFailure(command, Favourite.MESSAGE_FAVOURITE_CONSTRAINTS);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find favourite persons in address book -> 1 person found */
        command = FindCommand.COMMAND_WORD + " " + "favourite";
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
