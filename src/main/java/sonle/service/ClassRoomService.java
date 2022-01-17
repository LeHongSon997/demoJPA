package sonle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sonle.model.ClassRoom;
import sonle.repository.ClassRoomRepo;

import java.util.List;

@Repository
public class ClassRoomService implements IClassRoomService{
    @Autowired
    ClassRoomRepo classRoomRepo;

    @Override
    public List<ClassRoom> findAll() {
        return (List<ClassRoom>) classRoomRepo.findAll();
    }
}
