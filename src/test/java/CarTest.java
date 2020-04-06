import org.easymock.EasyMock;
import org.easymock.*;
import static org.easymock.EasyMock.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    private Car myFerrari;
    @BeforeEach
    public void setUp() {
        myFerrari = EasyMock.createMock(MockType.NICE, Car.class);
    }
    @Test
    public void test_instance_car(){
        assertTrue(myFerrari instanceof Car);
    }

    @Test
    public void test_default_behavior_needsFuel(){
        assertFalse(myFerrari.needsFuel(), "New test double should return False as boolean");
    }

    @Test
    public void test_default_behavior_temperature(){
        assertEquals(0.0, myFerrari.getEngineTemperature(), "New test double should return 0.0");
    }

    @Test
    public void test_stubbing_mock(){
        EasyMock.expect(myFerrari.needsFuel()).andReturn(true);
        EasyMock.replay(myFerrari);
        assertTrue(myFerrari.needsFuel());

    }

    @Test
    public void test_exception(){
        EasyMock.expect(myFerrari.needsFuel()).andThrow(new RuntimeException());
        EasyMock.replay(myFerrari);
        assertThrows(RuntimeException.class, () -> {
            myFerrari.needsFuel();
        });
    }

    @Test
    public void getVMaxTest() {
        EasyMock.expect(myFerrari.getVMax()).andReturn(Double.MAX_VALUE);
        EasyMock.replay(myFerrari);
        assertEquals(Double.MAX_VALUE, myFerrari.getVMax());

        verify(myFerrari);
    }

    @Test
    public void getVMaxExceptionTest() {
        EasyMock.expect(myFerrari.getVMax()).andThrow(new RuntimeException());
        replay(myFerrari);

        assertThrows(RuntimeException.class, () ->
                {myFerrari.getVMax();
            });
    }

    @Test
    public void getModelTest() {
        EasyMock.expect(myFerrari.getModel()).andReturn("F40");
        EasyMock.replay(myFerrari);
        assertEquals("F40", myFerrari.getModel());

        verify(myFerrari);
    }

    @Test
    public void getModelExceptionTest() {
        EasyMock.expect(myFerrari.getModel()).andThrow(new RuntimeException());
        replay(myFerrari);

        assertThrows(RuntimeException.class, () ->
        {myFerrari.getModel();
        });
    }

    @Test
    public void getEngineTemperatureTest() {
        EasyMock.expect(myFerrari.getEngineTemperature()).andReturn(Double.MAX_VALUE);
        EasyMock.replay(myFerrari);
        assertEquals(Double.MAX_VALUE, myFerrari.getEngineTemperature());

        verify(myFerrari);
    }

    @AfterEach
    public void tearDown() {
        myFerrari = null;
    }

}
