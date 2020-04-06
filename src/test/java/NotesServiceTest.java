import org.easymock.EasyMock;
import org.easymock.MockType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.easymock.EasyMock.*;

public class NotesServiceTest {

    private NotesService notesService;

    private NotesStorage notesStorage;

    @BeforeEach
    public void setUp() {
        notesStorage = mock(MockType.NICE, NotesStorage.class);
        notesService = mock(MockType.NICE, NotesService.class);
    }

    @Test
    public void addTest() {
        ArrayList<Note> notes = new ArrayList<Note>();
        Note note = Note.of("Kuba",5.0f);
        notesStorage.add(note);

        EasyMock.expectLastCall().andAnswer(() -> {
            notes.add(note);
            return null;
        }).times(1);
        replay(notesStorage);

        notesStorage.add(note);

        assertAll(
                () -> assertThat(notes).hasSize(1),
                () -> assertThat(notes.get(0).getName()).isEqualTo("Kuba"),
                () -> assertThat(notes.get(0).getNote()).isEqualTo(5.0f)
        );

        verify(notesStorage);
    }

    @Test
    public void clearListTest() {
        ArrayList<Note> notes = new ArrayList<Note>();
        notes.add(Note.of("Adam", 5.0f));

        notesService.clear();
        EasyMock.expectLastCall().andAnswer(() -> {
            notes.clear();
            return null;
        }).times(1);

        replay(notesService);

        notesService.clear();
        assertThat(notes).isEmpty();
        verify(notesService);
    }

    @Test
    public void avgTest() {
        float expected = 4.0f;

        expect(notesService.averageOf("Kuba")).andReturn(expected);
        replay(notesService);

        assertThat(notesService.averageOf("Kuba")).isEqualTo(expected);
        verify(notesService);
    }

    @Test
    public void givenNullNameToAvgTest() {
        expect(notesService.averageOf(null)).andThrow(new IllegalArgumentException("Imię ucznia nie może być null"));
        replay(notesService);

        assertThatThrownBy(() -> {
            notesService.averageOf(null);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Imię ucznia nie może być null");

        verify(notesService);
    }

    @Test
    public void givenEmptyNameToAvgTest() {
        expect(notesService.averageOf("")).andThrow(new IllegalArgumentException("Imię ucznia nie może być puste"));
        replay(notesService);

        assertThatThrownBy(() -> {
            notesService.averageOf("");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Imię ucznia nie może być puste");

        verify(notesService);
    }

    @AfterEach
    public void tearDown() {
        notesStorage = null;
        notesService = null;
    }


}
