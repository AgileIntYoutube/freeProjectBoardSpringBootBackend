package io.agileintelligence.projectboard.web;

import io.agileintelligence.projectboard.domain.ProjectTask;
import io.agileintelligence.projectboard.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
@CrossOrigin
public class ProjectTaskController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @PostMapping("")
    public ResponseEntity<?> addPTToBoard(@Valid @RequestBody ProjectTask projectTask , BindingResult result){

        // Check if there are errors
        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return  new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
        }

        //Save the new object
        ProjectTask newTask = projectTaskService.saveORupdateProjectTask(projectTask);
        //Return the object that was created
        return new ResponseEntity<ProjectTask>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/{pt_id}")
    public ResponseEntity<?> getPTById(@PathVariable Long pt_id){
        ProjectTask projectTask = projectTaskService.findById(pt_id);
        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    //Get a list of project tasks

    @GetMapping("/all")
    public Iterable<ProjectTask> getAllPTs(){
        return projectTaskService.findAll();
    }

    // We are not writing a single line of code!!

    @DeleteMapping("/{pt_id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long pt_id){
        projectTaskService.delete(pt_id);

        return new ResponseEntity<String>("Project Task with ID: "+pt_id+" was deleted", HttpStatus.OK);
    }
}
