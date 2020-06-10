package dk.nodes.template.models

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class EmployeesList (

        @SerializedName("data")
        var employeeList : ArrayList<Employee>



)