package dk.nodes.template.repositories

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import dk.nodes.template.models.Departments
import dk.nodes.template.models.DepartmentsInfo
import dk.nodes.template.models.EditedEmployee
import dk.nodes.template.models.Employee
import dk.nodes.template.network.IEmployesService
import java.util.ArrayList
import javax.inject.Inject

class EmployesRepository @Inject constructor(private val iEmployesService: IEmployesService,
                                             private val sharedPreferences: SharedPreferences,
                                             private val gson : Gson) {


    fun getHeader(): HashMap<String, String> {
        val mapHeader = HashMap<String, String>()
        val authToken = sharedPreferences.getString("access_token", null)

        if (authToken != null) {
            mapHeader.put("X-ClientId", "d2cc153a-3ad4-42b0-b832-43ee335e5ea5")
            mapHeader.put("Authorization", "Bearer $authToken")
        }
        return mapHeader


    }


    suspend fun fetchemployees(): ArrayList<Employee> {
        var employyeList = ArrayList<Employee>()

        val response = iEmployesService.fetchEmployees(getHeader()).execute()

        if (response.isSuccessful) {

            val message = response.body()

            if (message != null) {

                employyeList.addAll(message.employeeList)

            }

            }

            return employyeList




    }


        suspend fun fetchFromId(id: Int): Employee?{

        val response = iEmployesService.fetchEmployeesById(getHeader(), id).execute()

        if (response.isSuccessful) {
            var message = response.body()
            if (message != null) {
                return message
            }
        }
        return null
    }

    suspend fun fetchDepartementNames(): ArrayList<DepartmentsInfo>{

        val response = iEmployesService.fetchDepartmentNames(getHeader()).execute()
        val departmentList = ArrayList<DepartmentsInfo>()
        Log.d("Logoso","1")

        if (response.isSuccessful) {
            val message = response.body()
            Log.d("Logoso","1")

            if (message != null) {

                message.departmentsList?.let { departmentList.addAll(it) }

            }
        }

        return departmentList
    }

    suspend fun sendEmployee(employee: EditedEmployee): Unit?{

        var employeeHasmap = HashMap<String,String>()
        employeeHasmap.put("firstName",employee.firstName)
        employeeHasmap.put("lastName", employee.lastName)
        employeeHasmap.put("gender", employee.gender)


        val response = iEmployesService.sendEmployee(getHeader(),employee.id,gson.toJson(employeeHasmap)).execute()

        if (response.isSuccessful) {
            var message = response.body()
            if (message != null) {
                return null
            }
        }
        return null
    }
}







