package com.unisystems;

import com.unisystems.enums.EmployeeStatusEnum;
import com.unisystems.enums.TaskStatusEnum;
import com.unisystems.model.*;
import com.unisystems.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.unisystems")
@EntityScan("com.unisystems.model")
public class UniStructureApplication{

	public static void main(String[] args) {SpringApplication.run(UniStructureApplication.class, args);}

//	@Override
//	public void run(String... args) throws Exception {
//		Company firstCompany = new Company("UniSystems","That's the first company of this project and it listens to UniSystems");
//		companyRepository.save(firstCompany);
//
//		BusinessUnit publicSector = new BusinessUnit("PublicSector",
//				"That's the business unit that is well known as Public Sector",firstCompany);
//		BusinessUnit internationalSector = new BusinessUnit("InternationalSector",
//		"That's the business unit that is well known as International Sector",firstCompany);
//		businessUnitRepository.save(publicSector);
//		businessUnitRepository.save(internationalSector);
//
//		Department humanResources = new Department("HR", "That's the department that is well known as HR",publicSector);
//		Department salesDept = new Department("Sales", "That's the department that is well known as Sales Dept",publicSector);
//		Department deptOfMarketing = new Department("Marketing", "That's the department that is well known as Marketing Dept",internationalSector);
//		Department businessConsultants = new Department("BusinessConsultants", "That's the department that is well known as Business Consultants Dept",internationalSector);
//		departmentRepository.save(humanResources);
//		departmentRepository.save(salesDept);
//		departmentRepository.save(deptOfMarketing);
//		departmentRepository.save(businessConsultants);
//
//		Unit firstUnit = new Unit("FirstUnit", "That's the department that is well known as FU",humanResources);
//		Unit secondUnit = new Unit("SecondUnit", "That's the department that is well known as SU",salesDept);
//		Unit thirdUnit = new Unit("ThirdUnit", "That's the department that is well known as TU",deptOfMarketing);
//		Unit fourthUnit = new Unit("ForthUnit", "That's the department that is well known as FU",businessConsultants);
//
//		unitRepository.save(firstUnit);
//		unitRepository.save(secondUnit);
//		unitRepository.save(thirdUnit);
//		unitRepository.save(fourthUnit);
//
//		Employee george = new Employee(1187,"Kitsos","George",
//				"Voutza35","6972659243","2019-03-01",
//				null, EmployeeStatusEnum.ACTIVE,"UniSystems","JSE");
//		george.setEmployeeUnitRef(firstUnit);
//
//		Employee elias = new Employee(8843,"Kotsikonas",
//				"Elias","Stamatiadou5","6962538645",
//				"2007-03-01",null,EmployeeStatusEnum.ACTIVE,
//				"UniSystems","SSE");
//		elias.setEmployeeUnitRef(secondUnit);
//
//		Employee stelios = new Employee(8934,"Georgalas","Stelios",
//				"PeristeriStreet","6983648576","2019-11-03",
//				"2020-04-03",EmployeeStatusEnum.INACTIVE,"UniSystems","JJJSE");
//		stelios.setEmployeeUnitRef(firstUnit);
//
//		Employee dimitris = new Employee(2938,"Papanikolaou",
//				"Dimitris","Stamatiadou","6983628263",
//				"2019-11-09","2020-04-12",EmployeeStatusEnum.INACTIVE,
//				"UniSystems","HRConsultant");
//		dimitris.setEmployeeUnitRef(fourthUnit);
//
//		employeeRepository.save(dimitris);
//		employeeRepository.save(george);
//		employeeRepository.save(elias);
//		employeeRepository.save(stelios);
		//Tasks
//		Task newPortal = new Task("New Portal","This is a task for making a new portal",5,9,8, TaskStatusEnum.NEW);
//		newPortal.getUpdates().add("new");
//		newPortal.getEmployeesList().add(george);
//		taskRepository.save(newPortal);
//		Task newTroll = new Task("New trol","This is a task for making a new portal",3,4,4, TaskStatusEnum.NEW);
//		newTroll.getUpdates().add("trololo");
//		taskRepository.save(newTroll);
//	}
}
