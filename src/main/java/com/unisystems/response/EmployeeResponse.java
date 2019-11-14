package com.unisystems.response;

import com.unisystems.enums.EmployeeStatusEnum;

import java.util.Objects;

public class EmployeeResponse {
    private Long employeeId;
    private int registrationNumber;
    private String fullName; //firstName + lastName
    private String phoneNumber;
    private String workingPeriod;
    private EmployeeStatusEnum employeeStatus;
    private String contractType;
    private String employeeUnitName;
    private String position;

    public EmployeeResponse(Long employeeId, int registrationNumber, String fullName, String phoneNumber,
                            String workingPeriod, EmployeeStatusEnum employeeStatus, String contractType, String employeeUnitName, String position) {
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

    public EmployeeStatusEnum getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatusEnum employeeStatus) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeResponse)) return false;
        EmployeeResponse that = (EmployeeResponse) o;
        return getRegistrationNumber() == that.getRegistrationNumber() &&
                getEmployeeId().equals(that.getEmployeeId()) &&
                getFullName().equals(that.getFullName()) &&
                getPhoneNumber().equals(that.getPhoneNumber()) &&
                getWorkingPeriod().equals(that.getWorkingPeriod()) &&
                getEmployeeStatus() == that.getEmployeeStatus() &&
                getContractType().equals(that.getContractType()) &&
                getEmployeeUnitName().equals(that.getEmployeeUnitName()) &&
                getPosition().equals(that.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId(), getRegistrationNumber(), getFullName(), getPhoneNumber(), getWorkingPeriod(), getEmployeeStatus(), getContractType(), getEmployeeUnitName(), getPosition());
    }
}