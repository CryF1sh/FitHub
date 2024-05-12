import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun clearAuthToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }

    fun saveDiaryEntry(date: String, title: String) {
        val editor = sharedPreferences.edit()
        editor.putString("diary_entry_date", title)
        editor.apply()
    }

    fun getDiaryEntry(date: String): String? {
        return sharedPreferences.getString("diary_entry_date", null)
    }

    fun removeDiaryEntry(date: String) {
        sharedPreferences.edit().remove("diary_entry_date").apply()
    }

    fun clearAllDiaryEntries() {
        sharedPreferences.edit().let { editor ->
            sharedPreferences.all.keys.filter { it.startsWith("diary_entry_") }.forEach {
                editor.remove(it)
            }
            editor.apply()
        }
    }

    fun getAllDiaryEntries(): Map<String, *> {
        return sharedPreferences.all.filterKeys { it.startsWith("diary_entry_") }
    }

    fun saveLatestWorkoutPlanId(planId: Int, planName: String) {
        val editor = sharedPreferences.edit()
        editor.putInt("workout_plan_id_$planId", planId)
        editor.putString("workout_plan_name_$planId", planName)
        editor.apply()
    }


    fun getAllWorkoutPlanId(): Map<Int, String> {
        val workoutPlanIds = mutableMapOf<Int, String>()
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            if (key.startsWith("workout_plan_id")) {
                if (value is String) {
                    val planNameKey = key.replace("id", "name")
                    val planName = allEntries[planNameKey] as? String ?: ""
                    workoutPlanIds[value.toInt()] = planName
                }
            }
        }
        return workoutPlanIds
    }

}