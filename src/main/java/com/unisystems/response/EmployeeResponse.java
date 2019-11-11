package com.unisystems.response;

public class EmployeeResponse {
    private Long employeeId;
    private int registrationNumber;
    private String fullName; //firstName + lastName
    private String phoneNumber;
    private String workingPeriod;
    private boolean employeeStatus;
    private String contractType;
    private String employeeUnitName;
    private String position;

    public EmployeeResponse(Long employeeId, int registrationNumber, String fullName, String phoneNumber,
                            String workingPeriod, boolean employeeStatus, String contractType, String employeeUnitName, String position) {
        this.employeeId = employeeId;
        this.registrationNumber = registrationNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.workingPeriod = workingPeriod;
        this.employeeStatus = employeeStatus;
        this.contractType = contractType;
        this.employeeUnitName = employeeUnitName;
        this.position = position;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkingPeriod() {
        return workingPeriod;
    }

    public void setWorkingPeriod(String workingPeriod) {
        this.workingPeriod = workingPeriod;
    }

    public boolean isEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(boolean employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getEmployeeUnitName() {
        return employeeUnitName;
    }

    public void setEmployeeUnitName(String employeeUnitName) {
        this.employeeUnitName = employeeUnitName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}