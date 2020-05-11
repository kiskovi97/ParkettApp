package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.di.injector
import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceReadScreen
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.presenter.DanceReadPresenter
import kotlinx.android.synthetic.main.fragment_dance_read.*
import java.nio.ByteBuffer
import javax.inject.Inject


class DanceReadFragment : Fragment(), DanceReadScreen {

    @Inject
    lateinit var dancePresenter: DanceReadPresenter

    private val danceId by lazy { arguments!!.getInt(DANCE_ID) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injector.inject(this);
    }

    override fun onStart() {
        super.onStart()
        dancePresenter.attachScreen(this)
    }

    override fun onStop() {
        dancePresenter.detachScreen()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dance_read, container, false)
    }

    override fun onResume() {
        super.onResume()
        dancePresenter.showDance(danceId)
    }

    override fun showDance(dance: Dance?) {
        if (dance == null) {
            dance_textView.text = "DanceRead: No dance found"
        } else {
            dance_textView.text = dance.name + ": " + dance.content

            if (dance.dance_type?.image != null) {
                val base64string = dance.dance_type?.image?.substring(22)

                if (base64string != null) {
                    val imageBytes = Base64.decode(base64string, Base64.DEFAULT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val imageByteBuffer = ByteBuffer.wrap(imageBytes)
                        val source = ImageDecoder.createSource(imageByteBuffer)
                        val bitmap = ImageDecoder.decodeDrawable(source)
                        dance_image.setImageDrawable(bitmap)
                    } else {
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        dance_image.setImageBitmap(decodedImage)
                    }
                }
            }

        }
    }

    fun delete() {
        AlertDialog.Builder(context, R.style.AlertDialogTheme)
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
        fun newInstance(id: Int): DanceReadFragment {
            val fragment = DanceReadFragment()
            val bundle = Bundle()

            bundle.putInt(DANCE_ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
