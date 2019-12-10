package com.json;

public class JsonResponses {
    public static String jsonWithCriteria = "{\"employeeResponseList\":[{\"employeeId\":1,\"registrationNumber\":2938,\"fullName\":\"Dimitris Papanikolaou\",\"phoneNumber\":\"6983628263\",\"workingPeriod\":\"Years: 0, Months: 5, Days 3\",\"employeeStatus\":\"INACTIVE\",\"contractType\":\"UniSystems\",\"employeeUnitName\":\"ForthUnit\",\"position\":\"HRConsultant\"}]}";
    public static String jsonTaskById = "{\"taskResponses\":[{\"taskId\":15,\"taskTitle\":\"New Portal\",\"taskDesc\":\"This is a task for making a new portal\",\"difficulty\":\"HARD\",\"taskStatus\":\"NEW\",\"assignedEmployees\":[\"1187: George Kitsos, working at FirstUnit unit.\"],\"updates\":[\"new\"]}]}";
    public static String jsonResponseDeleteById = "[{\"errorCode\":234,\"errorMessage\":\"Task N/A\",\"description\":\"The provided taskId does not match with any task\"}]";
    public static String jsonCompanyResponse = "{\"companies\":[{\"companyId\":1,\"companyName\":\"UniSystems\",\"description\":\"That's the first company of this project and it listens to UniSystems\",\"title\":\"UniSystems, That's the first company of this project and it listens to UniSystems\"}]}";
}
