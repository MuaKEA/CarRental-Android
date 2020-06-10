package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

data class Departments(

       @SerializedName("data")
       var departmentsList : ArrayList<DepartmentsInfo>?
)



