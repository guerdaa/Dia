package com.android.dia.ui.add_measure

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.dia.R
import com.android.dia.broadcast.LantusAlarmReceiver
import com.android.dia.broadcast.MeasureAlarmReceiver
import com.android.dia.model.Measure
import com.android.dia.model.Settings
import com.android.dia.model.TypeOfDay
import com.android.dia.ui.InjectionDialogFragment
import com.android.dia.viewmodel_factories.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_measure_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddMeasureFragment : DaggerFragment() {

    private var typeOfDay = TypeOfDay.WORK
    private val measure = Measure()

    private var timeId = ""
    private var dateId = ""

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var settings: Settings

    private lateinit var viewModel: AddMeasureViewModel

    override fun onResume() {
        super.onResume()
        getTime()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_measure_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, providerFactory).get(AddMeasureViewModel::class.java)
        viewModel.uploaded.observe(viewLifecycleOwner, Observer {uploaded ->
            if(uploaded) {
                Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show()
            }
        })
        submit_btn.setOnClickListener {
            submitMeasure()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectTypeOfDay()
        getTime()
        getDate()
        getUnitsAndCorrections()
    }

    private fun submitMeasure() {
        try{
            measure_edit_text.clearFocus()
            carbohydrates_edit_text.clearFocus()
            measure.measure = measure_edit_text.getString().toInt()
            measure.khe = carbohydrates_edit_text.getString().toInt()
            measure.units = units_edit_text.getString().toInt()
            measure.correction = correction_edit_text.getString().toInt()
            measure.date = date_text_view.getString()
            measure.time = time_text_view.getString()
            measure.comment = comment_edit_text.getString()
            measure.typeOfDay = typeOfDay
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Please verify your input", Toast.LENGTH_SHORT).show()
            return
        }
        if(dateId.isNotEmpty() && timeId.isNotEmpty())
            measure.id = dateId + timeId
        viewModel.submit(measure)
        if(measure.units + measure.correction > 0 ) {
            InjectionDialogFragment.newInstance(measure.units.toString(), measure.correction.toString()).show(fragmentManager, "InjectionDialogFragment")
            settingAlarm()
        }
    }

    private fun selectTypeOfDay(): TypeOfDay {
        ic_relax.setOnClickListener {
            ic_relax.background = context?.getDrawable(R.drawable.selected_type)
            ic_sport.background = context?.getDrawable(R.drawable.non_selected_type)
            ic_work.background = context?.getDrawable(R.drawable.non_selected_type)
            typeOfDay = TypeOfDay.RELAX
        }

        ic_sport.setOnClickListener {
            ic_relax.background = context?.getDrawable(R.drawable.non_selected_type)
            ic_sport.background = context?.getDrawable(R.drawable.selected_type)
            ic_work.background = context?.getDrawable(R.drawable.non_selected_type)
            typeOfDay = TypeOfDay.SPORT
        }

        ic_work.setOnClickListener {
            ic_relax.background = context?.getDrawable(R.drawable.non_selected_type)
            ic_sport.background = context?.getDrawable(R.drawable.non_selected_type)
            ic_work.background = context?.getDrawable(R.drawable.selected_type)
            typeOfDay = TypeOfDay.WORK
        }

        return typeOfDay
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(){

        val cal = Calendar.getInstance()
        time_text_view.text = SimpleDateFormat("HH:mm").format(cal.time)
        measure.id = SimpleDateFormat("YYYYMMddHHmmss").format(cal.time)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            time_text_view.text = SimpleDateFormat("HH:mm").format(cal.time)
            timeId = SimpleDateFormat("HHmmss").format(cal.time)
        }

        time_text_view.setOnClickListener {
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(){

        val cal = Calendar.getInstance()

        date_text_view.text = SimpleDateFormat("dd.MM.YYYY").format(Date())
        measure.id = SimpleDateFormat("YYYYMMddHHmmss").format(cal.time)
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            dateId = ""
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)
            date_text_view.text = SimpleDateFormat("dd.MM.YYYY").format(cal.time)
            dateId = SimpleDateFormat("YYYYMMdd").format(cal.time)
        }

        date_text_view.setOnClickListener {
            DatePickerDialog(context!!, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun getUnitsAndCorrections() {
        carbohydrates_edit_text.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ ->
                if(measure_edit_text.getString().isNotEmpty() && carbohydrates_edit_text.getString().isNotEmpty()) {
                    correction_edit_text.setText(viewModel.getCorrection(measure_edit_text.getString().toInt()))
                    units_edit_text.setText(viewModel.getUnits(carbohydrates_edit_text.getString().toInt()))
                }
            }
        units_edit_text.onFocusChangeListener =
            View.OnFocusChangeListener { _, _ ->
                if(measure_edit_text.getString().isNotEmpty() && carbohydrates_edit_text.getString().isNotEmpty()) {
                    units_edit_text.setText(viewModel.getUnits(carbohydrates_edit_text.getString().toInt()))
                    correction_edit_text.setText(viewModel.getCorrection(measure_edit_text.getString().toInt()))
                }
            }

    }
    companion object {
        fun newInstance() = AddMeasureFragment()
    }

    //--------------------------------------------------------------------
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private fun settingAlarm() {
        alarmMgr?.cancel(alarmIntent)
        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, MeasureAlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        alarmMgr?.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + settings.measureAlarm * 60 * 60 * 1000,
            AlarmManager.INTERVAL_DAY,
            alarmIntent)
    }

}
