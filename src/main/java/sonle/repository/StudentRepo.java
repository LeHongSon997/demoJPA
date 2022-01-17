package sonle.repository;

import org.springframework.data.repository.CrudRepository;
import sonle.model.Student;

public interface StudentRepo extends CrudRepository<Student, Long> {
}
