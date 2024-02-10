package quarkusbug;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TestEntityService {

	@Inject
	private TestEntityRepository testEntityRepository;

	public TestEntity find(Long id) {
		return testEntityRepository.getEntityManager().find(TestEntity.class, id);
	}
	
	public void save(TestEntity entity) {
		testEntityRepository.getEntityManager().persist(entity);
	}
	
	public void update(TestEntity entity) {
		testEntityRepository.getEntityManager().merge(entity);
	}
	
	public void clean() {
		testEntityRepository.getEntityManager().createNativeQuery("DELETE FROM TEST_ENTITY").executeUpdate();
	}
}
