package dk.nodes.template.network

import dk.nodes.template.models.Departments
import dk.nodes.template.models.DepartmentsInfo
import dk.nodes.template.models.Employee
import dk.nodes.template.models.EmployeesList
import retrofit2.Call
import retrofit2.http.*

interface IEmployesService {


    @GET("/hr/v1/employees/{id}")
    fun fetchEmployeesById(@HeaderMap headers: Map<String, String>, @Path("id") id: Int): Call<Employee>

    @GET("hr/v1/employees")
    fun fetchEmployees(@HeaderMap headers: Map<String, String>): Call<EmployeesList>

    @GET("hr/v1/departments")
    fun fetchDepartmentNames(@HeaderMap headers: Map<String, String>): Call<Departments>

    @Headers("Content-Type: application/json")
    @PUT("hr/v1/employees/{id}")
    fun sendEmployee(@HeaderMap headers: Map<String, String>,@Path("id") id: Int,
                     @Body body :String): Call<String>


}