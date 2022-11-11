package goreset;

import actions.UserActions;
import asserts.UserAsserts;
import client.GoRestApiClient;
import io.nsingla.junit5.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import datamodels.gorest.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.List;

/**
 * An example Test class describing how to use the framework to write tests
 */
public class UserTests extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(UserTests.class);

    private GoRestApiClient client = new GoRestApiClient();

    /**
     * Get list of all users
     */
    @Test
    public void testGettingAllUsers() {
        List<User> usersList = UserActions.getUsers(client, new TypeReference<List<User>>() {
        });
        logger.trace(() -> "Users: " + usersList);
    }

    /**
     * Test creating a user
     */
    @Test
    public void testCreateUser() {
        String name = RandomStringUtils.randomAlphanumeric(6, 20);
        User expectedUser = new User()
            .setName(name)
            .setGender("male")
            .setEmail(name + "@test.com")
            .setStatus("active");
        User createdUser = UserActions.createUser(client, expectedUser, User.class);
        //Assert 2 user objects
        UserAsserts.assertUser(expectedUser, createdUser);
    }
}
