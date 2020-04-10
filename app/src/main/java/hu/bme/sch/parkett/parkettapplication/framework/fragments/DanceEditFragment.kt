package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.di.injector
import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.presenter.DanceEditPresenter
import kotlinx.android.synthetic.main.fragment_dance_edit.*
import javax.inject.Inject

class DanceEditFragment : Fragment(), DanceEditScreen {

    @Inject
    lateinit var dancePresenter: DanceEditPresenter

    private var selectedDance: Dance? = null

    private val danceId by lazy { arguments!!.getInt(DanceEditFragment.DANCE_ID) }

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
        dancePresenter.showDance(danceId)
        saveButton.setOnClickListener {
            save()
        }
        deleteButton.setOnClickListener {
            delete()
        }
    }

    override fun showDance(dance: Dance?) {
        if (dance != null) {
            dance_edit_textView.text = "Edit: ${dance.id} ${dance.name}"
            editDanceName.setText(dance.name)
            editDanceContent.setText(dance.content)

        } else {
            dance_edit_textView.text = "Edit: No Dance found"
        }
        selectedDance = dance
    }

    fun save() {
        selectedDance?.name = editDanceName.text.toString()
        selectedDance?.content = editDanceContent.text.toString()
        selectedDance?.let { dancePresenter.saveDance(it) }
    }

    fun delete() {
        selectedDance?.let { dancePresenter.deleteDance(selectedDance!!.id)}
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
