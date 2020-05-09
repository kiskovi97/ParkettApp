package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.di.injector
import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceListScreen
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.presenter.DanceListPresenter
import kotlinx.android.synthetic.main.fragment_dance_list.*
import javax.inject.Inject

class DanceListFragment : Fragment(), DanceListScreen {

    @Inject
    lateinit var danceListPresenter: DanceListPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this);
    }

    override fun onStart() {
        super.onStart()
        danceListPresenter.attachScreen(this)
    }

    override fun onStop() {
        danceListPresenter.detachScreen()
        super.onStop()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dance_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        danceListPresenter.refreshDanceList()

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            danceListView.layoutManager = GridLayoutManager(context, 3)
        } else {
            danceListView.layoutManager = GridLayoutManager(context, 2)
        }

        danceListView.setHasFixedSize(true)
    }

    override fun showDanceList(result: List<Dance>) {
        danceListView.adapter = DanceAdapter(result)
    }

    companion object {
        fun newInstance(): DanceListFragment = DanceListFragment()
    }
}
