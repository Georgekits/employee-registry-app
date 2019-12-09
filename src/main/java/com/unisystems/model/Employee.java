package com.unisystems.model;

import com.unisystems.enums.EmployeeStatusEnum;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

@Entity
@Embeddable
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;
    @Column(name = "REGISTRATION_NUMBER")
    private int registrationNumber;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "ADDRESS_STREET")
    private String addressStreet;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber; //It can get the + symbol
    @Column(name = "RECRUITMENT_DATE")
    private Date recruitmentDate;
    @Column(name = "RELEASE_DATE", nullable = true)
    private Date releaseDate;
    @Column(name = "EMPLOYEE_STATUS") //boolean does not exits as SQL data type, H2 converts it to int? tinyInt?
    private EmployeeStatusEnum employeeStatus;
    @Column(name = "CONTRACT_TYPE")
    private String contractType;
    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_UNIT_REF", referencedColumnName = "UNIT_ID")
    private Unit employeeUnitRef;
    @Column(name = "POSITION")
    private String position;

    public Employee() {}

    public Employee(int registrationNumber, String lastName, String firstName, String addressStreet,
                    String phoneNumber, String recruitmentDate, String releaseDate,
                    EmployeeStatusEnum employeeStatus, String contractType, String position) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Set local timezone
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.recruitmentDate = recruitmentDate == null ? null : formatter.parse(recruitmentDate);
            this.releaseDate = releaseDate == null ? null : formatter.parse(releaseDate);
        } catch(Exception e) {
            System.out.println("Something went wrong with the date parsing, Employee constructor");
            e.printStackTrace();
        }
        this.registrationNumber = registrationNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.addressStreet = addressStreet;
        this.phoneNumber = phoneNumber;
        this.employeeStatus = employeeStatus;
        this.contractType = contractType;
        this.position = position;
    }

    public Employee(Long employeeId, int registrationNumber, String lastName, String firstName, String addressStreet,
                    String phoneNumber, String recruitmentDate, String releaseDate,
                    EmployeeStatusEnum employeeStatus, String contractType, String position) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Set local timezone
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.recruitmentDate = recruitmentDate == null ? null : formatter.parse(recruitmentDate);
            this.releaseDate = releaseDate == null ? null : formatter.parse(releaseDate);
        } catch(Exception e) {
            System.out.println("Something went wrong with the date parsing, Employee constructor");
            e.printStackTrace();
        }
        this.employeeId = employeeId;
        this.registrationNumber = registrationNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.addressStreet = addressStreet;
        this.phoneNumber = phoneNumber;
        this.employeeStatus = employeeStatus;
        this.contractType = contractType;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRecruitmentDate() {
        return recruitmentDate;
    }

    public void setRecruitmentDate(Date recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public Unit getEmployeeUnitRef() {
        return employeeUnitRef;
    }

    public void setEmployeeUnitRef(Unit employeeUnitRef) {
        this.employeeUnitRef = employeeUnitRef;
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
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getRegistrationNumber() == employee.getRegistrationNumber() &&
                getEmployeeId().equals(employee.getEmployeeId()) &&
                getLastName().equals(employee.getLastName()) &&
                getFirstName().equals(employee.getFirstName()) &&
                getAddressStreet().equals(employee.getAddressStreet()) &&
                getPhoneNumber().equals(employee.getPhoneNumber()) &&
                getRecruitmentDate().equals(employee.getRecruitmentDate()) &&
                getReleaseDate().equals(employee.getReleaseDate()) &&
                getEmployeeStatus() == employee.getEmployeeStatus() &&
                getContractType().equals(employee.getContractType()) &&
                getEmployeeUnitRef().equals(employee.getEmployeeUnitRef()) &&
                getPosition().equals(employee.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId(), getRegistrationNumber(), getLastName(), getFirstName(), getAddressStreet(), getPhoneNumber(), getRecruitmentDate(), getReleaseDate(), getEmployeeStatus(), getContractType(), getEmployeeUnitRef(), getPosition());
    }
}