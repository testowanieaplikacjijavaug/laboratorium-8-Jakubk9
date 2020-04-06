import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static org.assertj.core.api.Assertions.assertThat;
import static org.easymock.EasyMock.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(EasyMockExtension.class)
public class FriendshipsMongoEasyMockTest {

	@TestSubject
	FriendshipsMongo friendships = new FriendshipsMongo();

	//A nice mock expects recorded calls in any order and returning null for other calls
	@Mock(type = MockType.NICE)
	FriendsCollection friends;
	
	@Test
	public void mockingWorksAsExpected(){
		Person joe = new Person("Joe");
		//Zapisanie zachowania - co sie ma stac
		expect(friends.findByName("Joe")).andReturn(joe);
		//Odpalenie obiektu do sprawdzenia zachowania
		replay(friends);
		assertThat(friends.findByName("Joe")).isEqualTo(joe);
	}
	
	@Test
	public void alexDoesNotHaveFriends(){
		assertThat(friendships.getFriendsList("Alex")).isEmpty();
	}
	
	@Test
	public void joeHas5Friends(){
		List<String> expected = Arrays.asList(new String[]{"Karol","Dawid","Maciej","Tomek","Adam"});
		Person joe = createMock(Person.class);
		expect(friends.findByName("Joe")).andReturn(joe);
		expect(joe.getFriends()).andReturn(expected);
		replay(friends);
		replay(joe);
		assertThat(friendships.getFriendsList("Joe")).hasSize(5).containsOnly("Karol","Dawid","Maciej","Tomek","Adam");
	}

	@Test
	public void joeNameIsJoe() {
		Person joe = createMock(Person.class);
		expect(joe.getName()).andReturn("Joe");
		replay(joe);

		assertThat(joe.getName()).isEqualTo("Joe");
	}

	@Test
	public void setEmptyNameShouldThrowAnExceptionTest() {
		Person joe = createMock(Person.class);
		joe.setName("");
		expectLastCall().andThrow(new RuntimeException());
	}

	@Test
	public void addEmptyFriendShouldThrowAnException() {
		Person joe = createMock(Person.class);
		joe.addFriend("");
		expectLastCall().andThrow(new IllegalArgumentException());
	}

	@Test
	public void joeAndAlexAreFriends() {
		List<String> alexFriends = Collections.singletonList("Joe");
		List<String> joeFriends = Collections.singletonList("Alex");

		Person alex = createMock(Person.class);
		Person joe = createMock(Person.class);

		expect(alex.getFriends()).andReturn(alexFriends);
		expect(joe.getFriends()).andReturn(joeFriends);
		expect(friends.findByName("Alex")).andReturn(alex);
		expect(friends.findByName("Joe")).andReturn(joe);

		replay(alex);
		replay(joe);
		replay(friends);

		assertThat(friendships.areFriends("Joe","Alex")).isTrue();
	}

	@Test
	public void joeAndAlexAreNotFriends() {
		List<String> alexFriends = Collections.singletonList("NotAJoe");
		List<String> joeFriends = Collections.singletonList("NotAAlex");

		Person alex = createMock(Person.class);
		Person joe = createMock(Person.class);

		expect(alex.getFriends()).andReturn(alexFriends);
		expect(joe.getFriends()).andReturn(joeFriends);
		expect(friends.findByName("Alex")).andReturn(alex);
		expect(friends.findByName("Joe")).andReturn(joe);

		replay(alex);
		replay(joe);
		replay(friends);

		assertThat(friendships.areFriends("Joe","Alex")).isFalse();
	}

}
