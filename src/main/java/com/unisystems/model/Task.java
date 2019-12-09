package com.unisystems.model;

import com.unisystems.enums.TaskStatusEnum;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ESTIMATION_A")
    private int estimationA;
    @Column(name = "ESTIMATION_B")
    private int estimationB;
    @Column(name = "ESTIMATION_C")
    private int estimationC;
    @Column(name = "STATUS")
    private TaskStatusEnum status;
    @ElementCollection
    @Column(name = "UPDATES")
    private List<String> updates = new ArrayList<String>();
    @Column(name = "EMPLOYEE_LIST")
    @ManyToMany
    private List<Employee> employeesList = new ArrayList<Employee>();


    public Task() {}

    public Task(String title, String desc, int estimationA, int estimationB, int estimationC, TaskStatusEnum status) {
        this.title = title;
        this.description = desc;
        this.estimationA = estimationA;
        this.estimationB = estimationB;
        this.estimationC = estimationC;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEstimationA() {
        return estimationA;
    }

    public void setEstimationA(int estimationA) {
        this.estimationA = estimationA;
    }

    public int getEstimationB() {
        return estimationB;
    }

    public void setEstimationB(int estimationB) {
        this.estimationB = estimationB;
    }

    public int getEstimationC() {
        return estimationC;
    }

    public void setEstimationC(int estimationC) {
        this.estimationC = estimationC;
    }

    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }

    public List<String> getUpdates() {
        return updates;
    }

    public void setUpdates(List<String> updates) {
        this.updates = updates;
    }

    public List<Employee> getEmployeesList() {
        return employeesList;
    }

    public List<String> getEmployeeInfo(Task task){
        List<String> employees = new ArrayList<String>();
        if(task.getEmployeesList() != null && task.getEmployeesList().size() != 0){
            for(Employee emp: task.getEmployeesList()){
                String empInfo = emp.getRegistrationNumber()+": "+emp.getFirstName()+" "+emp.getLastName()+", " +
                        "working at "+emp.getEmployeeUnitRef().getUnitName()+" unit.";
                employees.add(empInfo);
            }
        }
        return employees;
    }
    public void setEmployeesList(List<Employee> employeesList) {
        this.employeesList = employeesList;
    }
}
