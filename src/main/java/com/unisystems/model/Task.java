package com.unisystems.model;

import com.unisystems.enums.TaskStatusEnum;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESC")
    private String desc;
    @Column(name = "ESTIMATION_A")
    private int estimationA;
    @Column(name = "ESTIMATION_B")
    private int estimationB;
    @Column(name = "ESTIMATION_C")
    private int estimationC;
    @Column(name = "STATUS")
    private TaskStatusEnum status;
    @ElementCollection
    private List<String> updates = new ArrayList<String>();
    @ManyToMany
    @JoinColumn(name = "EMPLOYEE_REF", referencedColumnName = "employeeId")
    private List<Employee> employeesList;

    public Task() {}

    public Task(String title, String desc, int estimationA, int estimationB, int estimationC, TaskStatusEnum status) {
        this.title = title;
        this.desc = desc;
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
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Employee> getEmployeeRef() {
        return employeesList;
    }

    public void setEmployeeRef(List<Employee> employee) {
        this.employeesList = employee;
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
}
