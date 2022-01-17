package sonle.service;

import org.springframework.beans.factory.annotation.Autowired;
import sonle.model.Student;
import sonle.repository.StudentRepo;

import java.util.List;
import java.util.Optional;

public class StudentService implements IStudentService{
    @Autowired
    StudentRepo studentRepo;

    @Override
    public List<Student> findAll() {
        return (List<Student>) studentRepo.findAll();
    }

    @Override
    public void save(Student student) {
        studentRepo.save(student);
    }

    @Override
    public void delete(long id) {
        studentRepo.deleteById(id);
    }

    @Override
    public Optional<Student> findById(long id) {
        return studentRepo.findById(id);
    }
}
