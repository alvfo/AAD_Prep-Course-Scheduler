package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))

        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            when (newValue.toString()) {
                getString(R.string.pref_dark_off) -> updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                getString(R.string.pref_dark_on) -> updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                else -> updateTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            true
        }

        // TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val dailyReminder = DailyReminder()
        val notificationPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notificationPreference?.setOnPreferenceChangeListener { _, newValue ->
            val isEnabled = newValue as Boolean
            if (isEnabled) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
    }
}