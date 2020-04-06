import org.easymock.EasyMockExtension;
import org.easymock.MockType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.mock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EasyMockExtension.class)
public class MockTypeTest {

    @Test
    public void strickMockTest() {
        ArrayList<Integer> mockedList = mock(MockType.STRICT, ArrayList.class);

        expect(mockedList.add(20)).andReturn(true);
        expect(mockedList.add(30)).andReturn(true);
        expect(mockedList.size()).andReturn(2);
        expect(mockedList.get(1)).andReturn(30);
        replay(mockedList);

        mockedList.add(20);
        mockedList.add(30);

        assertEquals(2, mockedList.size());
        assertEquals(30, (int) mockedList.get(1));

        verify(mockedList);
    }

    @Test
    public void niceMockTest() {
        ArrayList<Integer> mockedList = mock(MockType.NICE, ArrayList.class);

        expect(mockedList.add(20)).andReturn(true);
        expect(mockedList.size()).andReturn(2);
        expect(mockedList.get(0)).andReturn(20);
        replay(mockedList);

        mockedList.add(20);

        //nice mock allows to do this
        boolean b = mockedList.add(30);
        assertFalse(b);

        assertEquals(2, mockedList.size());
        assertEquals(20, (int) mockedList.get(0));

        verify(mockedList);
    }

    @Test
    public void defaultMockTest() {
        ArrayList<Integer> mockedList = mock(MockType.DEFAULT, ArrayList.class);

        expect(mockedList.add(20)).andReturn(true);
        expect(mockedList.size()).andReturn(2);
        expect(mockedList.get(1)).andReturn(30);
        replay(mockedList);

        mockedList.add(20);

        assertEquals(2, (int) mockedList.size());
        assertEquals(30, (int) mockedList.get(1));

        verify(mockedList);
    }
}
