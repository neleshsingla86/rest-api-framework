package asserts;

import datamodels.gorest.User;
import org.junit.jupiter.api.Assertions;

public class UserAsserts extends AssertsCommon {

    public static void assertUser(User expectedUser, User actualUser) {
        Assertions.assertNotNull(actualUser);
        Assertions.assertNotNull(actualUser.getId(), "Id null for the created user");
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail(), getMessage("Email"));
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName(), getMessage("Name"));
        Assertions.assertEquals(expectedUser.getStatus(), actualUser.getStatus(), getMessage("Status"));
        Assertions.assertEquals(expectedUser.getGender(), actualUser.getGender(), getMessage("Gender"));
    }
}
