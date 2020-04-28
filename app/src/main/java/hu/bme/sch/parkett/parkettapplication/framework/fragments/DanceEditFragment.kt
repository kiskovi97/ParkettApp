package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.di.injector
import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.presenter.DanceEditPresenter
import kotlinx.android.synthetic.main.fragment_dance_edit.*
import javax.inject.Inject

class DanceEditFragment : Fragment(), DanceEditScreen {

    @Inject
    lateinit var dancePresenter: DanceEditPresenter

    private var selectedDance: Dance = Dance(-1,null, null, null)

    private val danceId by lazy { arguments!!.getInt(DanceEditFragment.DANCE_ID) }

    lateinit var options: MutableList<DanceType>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this);
        dancePresenter.attachScreen(this)
    }

    override fun onDetach() {
        dancePresenter.detachScreen()
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dance_edit, container, false)
    }

    override fun onResume() {
        super.onResume()
        dancePresenter.refreshDanceTypeList()
        if (danceId >= 0) {
            dancePresenter.showDance(danceId)
        }
        saveButton.setOnClickListener {
            save()
        }
    }

    override fun showDance(dance: Dance?) {
        if (dance != null) {
            editDanceName.setText(dance.name)
            editDanceContent.setText(dance.content)
            selectedDance = dance
            val index = options.indexOf(selectedDance.dance_type)

            if (index >= 0) {
                spinnerDanceType.setSelection(index)
            }
        }
    }

    override fun setDanceTypeList(danceList: List<DanceType>) {
        options = danceList.toMutableList()

        spinnerDanceType.adapter = DanceTypeSpinnerAdapter(activity!!, R.layout.dance_type_spinner_item, options)

        val index = options.indexOf(selectedDance.dance_type)

        if (index >= 0) {
            spinnerDanceType.setSelection(index)
        }

        spinnerDanceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedDance.dance_type = null
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position >= 0 && options.count() > position) {
                    selectedDance.dance_type = options[position]
                }
            }
        }
    }

    private fun save() {
        Log.d("Saving", selectedDance.toString())

        selectedDance.name = editDanceName.text.toString()
        selectedDance.content = editDanceContent.text.toString()
        dancePresenter.saveDance(selectedDance)
        activity?.finish()
    }

    fun delete() {
        AlertDialog.Builder(context)
                .setTitle("Deleting dance")
                .setMessage("Are you sure you want to delete dance with id: " + danceId)
                .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                    dancePresenter.deleteDance(danceId)
                    activity?.finish()
                }
                .setNegativeButton("No", null)
                .show()
    }

    companion object {
        private const val DANCE_ID = "DANCE_ID"
        fun newInstance(id: Int): DanceEditFragment {
            val fragment = DanceEditFragment()
            val bundle = Bundle()

            bundle.putInt(DANCE_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
