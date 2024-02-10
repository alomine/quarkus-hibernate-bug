package quarkusbug;

import java.util.Date;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import jakarta.inject.Inject;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class TestBugTest {

	@Inject
	TestEntityService testEntityService;
	@Inject
	TestEntityRepository testEntityRepository;
	private Date justDate = new Date();

	@Test
	public void saveAndCheck_test() {
		testEntityService.clean();

		TestEntity testEntity = new TestEntity(1l, 1, "a", justDate);
		testEntityService.save(testEntity);
		assertEquals(1, testEntityService.find(1l).getNumberField());
	}

	//no update to database is made
	@Test
	public void saveAndCheckWithNonNullNumber_test() {
		testEntityService.clean();

		TestEntity testEntity = createEntityWithNonNullNumber();
		testEntityService.save(testEntity);
		assertEquals(1, testEntityService.find(1l).getNumberField());

		testEntity = createEntityWithAllNulls();
		testEntityService.update(testEntity);
		assertEquals(null, testEntityService.find(1l).getNumberField());
	}
	
	//no update to database is made
	@Test
	public void saveAndCheckWithNonNullString_test() {
		testEntityService.clean();

		TestEntity testEntity = createEntityWithNonNullString();
		testEntityService.save(testEntity);
		assertEquals("a", testEntityService.find(1l).getStringField());

		TestEntity testEntity2 = createEntityWithAllNulls();
		testEntityService.update(testEntity2);
		assertEquals(null, testEntityService.find(1l).getStringField());
	}

	@Test
	public void saveAndCheckWithNonNullDate_test() {
		testEntityService.clean();

		TestEntity testEntity = createEntityWithNonNullDate();
		testEntityService.save(testEntity);
		assertEquals(justDate, testEntityService.find(1l).getDateField());

		testEntity = createEntityWithAllNulls();
		testEntityService.update(testEntity);
		assertEquals(null, testEntityService.find(1l).getDateField());
	}

	@Test
	public void saveAndCheckWithNonNullNumber_test2() {
		testEntityService.clean();

		TestEntity testEntity = createFullEntity();
		testEntityService.save(testEntity);
		assertEquals(1, testEntityService.find(1l).getNumberField());

		testEntity = createEntityWithNullNumber();
		testEntityService.update(testEntity);
		assertEquals(null, testEntityService.find(1l).getNumberField());
	}

	@Test
	public void saveAndCheckWithNonNullString_test2() {
		testEntityService.clean();

		TestEntity testEntity = createFullEntity();
		testEntityService.save(testEntity);
		assertEquals("a", testEntityService.find(1l).getStringField());

		testEntity = createEntityWithNullString();
		testEntityService.update(testEntity);
		assertEquals(null, testEntityService.find(1l).getStringField());
	}

	@Test
	public void saveAndCheckWithNonNullDate_test2() {
		testEntityService.clean();

		TestEntity testEntity = createFullEntity();
		testEntityService.save(testEntity);
		assertEquals(justDate, testEntityService.find(1l).getDateField());

		testEntity = createEntityWithNullDate();
		testEntityService.update(testEntity);
		assertEquals(null, testEntityService.find(1l).getDateField());
	}

	private TestEntity createFullEntity() {
		return new TestEntity(1l, 1, "a", justDate);
	}

	private TestEntity createEntityWithNullNumber() {
		return new TestEntity(1l, null, "a", justDate);
	}

	private TestEntity createEntityWithNullString() {
		return new TestEntity(1l, 1, null, justDate);
	}

	private TestEntity createEntityWithNullDate() {
		return new TestEntity(1l, 1, "a", null);
	}

	private TestEntity createEntityWithNonNullNumber() {
		return new TestEntity(1l, 1, null, null);
	}

	private TestEntity createEntityWithNonNullString() {
		return new TestEntity(1l, null, "a", null);
	}

	private TestEntity createEntityWithNonNullDate() {
		return new TestEntity(1l, null, null, justDate);
	}

	private TestEntity createEntityWithAllNulls() {
		return new TestEntity(1l, null, null, null);
	}
}
