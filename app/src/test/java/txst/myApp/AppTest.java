package txst.myApp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @Test
    void appHasGreeting() {
        App classUnderTest = new App();

        assertEquals("Todo List application ready.", classUnderTest.getGreeting());
    }
}
