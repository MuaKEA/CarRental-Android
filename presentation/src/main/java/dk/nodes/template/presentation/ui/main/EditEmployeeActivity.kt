package dk.nodes.template.presentation.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import dk.nodes.template.models.EditedEmployee
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_employee.*


class EditEmployeeActivity : BaseActivity(), View.OnClickListener {

    private val viewModel by viewModel<MainActivityViewModel>()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_employee)

        getValuesFromBundle()
        SetListners()

        viewModel.viewState.observeNonNull(this) { state ->
            fetchemploye(state)

        }

    }

    private fun getValuesFromBundle() {
        val bundle: Bundle? = intent.extras

        if (bundle?.getInt("empId") != null) {
            id =bundle.getInt("empId", -1)
            viewModel.fetchfromID(id)
        }
    }



    private fun SetListners() {
        send_btn.setOnClickListener(this)
        cancel_btn.setOnClickListener(this)
    }

    private fun fetchemploye(viewState: MainActivityViewState) {
        viewState.employee.let { employee ->

            firstName_edit.setText(employee?.firstName)
            lastName_edit.setText(employee?.lastName)
            Picasso.get().load("https://picsum.photos/1080/1920").memoryPolicy(MemoryPolicy.NO_CACHE).fit().into(imageView)

            val spinnerValues = resources.getStringArray(R.array.gender_array)

            employee?.gender?.let { gender ->

                spinnerValues.forEachIndexed { index, element ->

                    if (employee.gender == element) {
                        gender_spinner.setSelection(index)
                    }
                }
            }


        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {


            send_btn.id ->
                sendEmployee()

            cancel_btn.id ->
                finish()
        }

    }

    private fun sendEmployee() {
        viewModel.sendEmployee(getValues())
        finish()

    }

    fun getValues() : EditedEmployee {
        return EditedEmployee(id,firstName_edit.text.toString(), lastName_edit.text.toString(), gender_spinner.getItemAtPosition(gender_spinner.selectedItemPosition).toString())

}
}
