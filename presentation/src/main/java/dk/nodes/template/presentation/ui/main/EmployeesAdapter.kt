package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Employee
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.employees_row.view.*
import timber.log.Timber

class EmployeesAdapter(val context: Context, val recyclerviewRow: Int) : RecyclerView.Adapter<ViewHolder>() {
    var onItemClickedListener: ((employee: Employee) -> Unit?)? = null


    val employeesList: ArrayList<Employee> = ArrayList()
    // Gets the number of employeesList in the list
    override fun getItemCount(): Int {
        return employeesList.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(recyclerviewRow, parent, false))
    }

    // Binds each employeesList in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.root.setOnClickListener {
            onItemClickedListener?.invoke(employeesList.get(position))
        }
        var dptNames = ""
        holder.firstname?.text = employeesList.get(position).firstName
        holder.lastname?.text = employeesList.get(position).lastName


        employeesList[position].departments?.forEachIndexed { index, element ->
            dptNames = dptNames + element  +"\n"


        }


        holder.departmentment?.text = dptNames


    }


    fun addemployeesList(list: ArrayList<Employee>) {
        employeesList.clear()
        Timber.e(list.toString())
        employeesList.addAll(list)
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each employeesList to
    val firstname = itemView.firstname_info
    val lastname = view.lastname_info
    val departmentment = view.departments_info
    val root = view.employee_item


}