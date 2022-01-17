package sonle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sonle.model.ClassRoom;
import sonle.model.Student;
import sonle.service.IClassRoomService;
import sonle.service.IStudentService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    IStudentService studentService;

    @Autowired
    IClassRoomService classRoomService;

    @GetMapping("/students")
    public ModelAndView showAll(){
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("students", studentService.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("student", new Student());
        modelAndView.addObject("classRooms", classRoomService.findAll());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(value = "student") Student student, @RequestParam long idClassZoom, @RequestParam MultipartFile upImg){
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(idClassZoom);
        student.setClassRoom(classRoom);

        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("\\C:\\Users\\a\\Desktop\\Demo_JPA\\src\\main\\webapp\\WEB-INF\\img\\" + nameFile));
            student.setImg("/img/"+nameFile);
            studentService.save(student);

        } catch (IOException e) {
            student.setImg("");
            studentService.save(student);
            e.printStackTrace();
        }
        return "redirect:/students";
    }

    @GetMapping("/edit{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (student.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("edit");
            modelAndView.addObject("student", student.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public String updateCustomer(@ModelAttribute(value = "student") Student student,@RequestParam long idClassZoom, @RequestParam MultipartFile upImg) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(idClassZoom);
        student.setClassRoom(classRoom);
        if(upImg.getSize()!=0){
            File oldFile = new File("\\C:\\Users\\a\\Desktop\\Demo_JPA\\src\\main\\webapp\\WEB-INF\\img\\"+ student.getImg());
            oldFile.delete();
            String nameFile = upImg.getOriginalFilename();
            try {
                FileCopyUtils.copy(upImg.getBytes(),new File("\\C:\\Users\\a\\Desktop\\Demo_JPA\\src\\main\\webapp\\WEB-INF\\img\\"+ nameFile));
                student.setImg("/img/"+nameFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        studentService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/delete{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Student> student = studentService.findById(id);
        if (student.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("delete");
            modelAndView.addObject("student", student.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteCustomer(@ModelAttribute("student") Student student) {
        File oldFile = new File("\\C:\\Users\\a\\Desktop\\Demo_JPA\\src\\main\\webapp\\WEB-INF\\img\\"+student.getImg());
        oldFile.delete();
        studentService.delete(student.getId());
        return "redirect:students";
    }
}
