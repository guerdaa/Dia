package com.android.dia.ui.settings

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.android.dia.R
import com.android.dia.broadcast.LantusAlarmReceiver
import com.android.dia.model.Settings
import com.android.dia.ui.HelpDialogFragment
import com.android.dia.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.submit_btn
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SettingsFragment : DaggerFragment() {

    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var firestore: FirebaseFirestore

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.settings_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        submit_btn.setOnClickListener {
            submit()
        }
        help.setOnClickListener {
            showHelpDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        morning_factor_edit_text.setText(settings.morningFactor.toString())
        afternoon_factor_edit_text.setText(settings.afternoonFactor.toString())
        night_factor_edit_text.setText(settings.nightFactor.toString())
        lantus_alarm_edit_text.text = settings.lantusAlarm
        lantus_units_edit_text.setText(settings.lantusUnits.toString())
        measure_alarm_edit_text.setText(settings.measureAlarm.toString())
        getTime()
    }
    private fun submit() {
        try {
            settings.morningFactor = morning_factor_edit_text.getString().toInt()
            settings.afternoonFactor = afternoon_factor_edit_text.getString().toInt()
            settings.nightFactor = night_factor_edit_text.getString().toInt()
            settings.lantusUnits = lantus_units_edit_text.getString().toInt()
            settings.lantusAlarm = lantus_alarm_edit_text.getString()
            settings.measureAlarm = measure_alarm_edit_text.getString().toInt()

        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Please check your input", Toast.LENGTH_LONG).show()
            return
        }
        sharedPreferencesEditor.putInt(Constants.MORNING_FACTOR_PREF, settings.morningFactor)
        sharedPreferencesEditor.putInt(Constants.AFTERNOON_FACTOR_PREF, settings.afternoonFactor)
        sharedPreferencesEditor.putInt(Constants.NIGHT_FACTOR_PREF, settings.nightFactor)
        sharedPreferencesEditor.putInt(Constants.LANTUS_UNITS_PREF, settings.lantusUnits)
        sharedPreferencesEditor.putString(Constants.LANTUS_ALARM_PREF, settings.lantusAlarm)
        sharedPreferencesEditor.putInt(Constants.MEASURE_ALARM_PREF, settings.measureAlarm)

        sharedPreferencesEditor.apply()
        Toast.makeText(context, "Settings updated", Toast.LENGTH_SHORT).show()
        settingAlarm()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(){

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            lantus_alarm_edit_text.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        lantus_alarm_edit_text.setOnClickListener {
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(
                Calendar.MINUTE), true).show()
        }
    }

    private fun showHelpDialog() {
        HelpDialogFragment.newInstance().show(fragmentManager, "HelpDialogFragment")
    }
    //-----------------------------------------------------------------------------------------

    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private fun settingAlarm() {
        alarmMgr?.cancel(alarmIntent)
        val times = settings.lantusAlarm.split(":")
        alarmMgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, times[0].toInt())
            set(Calendar.MINUTE, times[1].toInt())
        }

        alarmIntent = Intent(context, LantusAlarmReceiver::class.java).let { intent ->
            intent.putExtra("TIME", calendar.timeInMillis)
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmIntent)
    }

}
