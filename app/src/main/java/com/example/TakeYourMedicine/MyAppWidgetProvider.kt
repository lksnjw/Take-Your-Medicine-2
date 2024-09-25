package com.example.TakeYourMedicine

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.TakeYourMedicine.model.Repositories
import com.example.TakeYourMedicine.model.WorkResult // Replace this with your actual result class
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val tasksList = mutableListOf<String>()
                Repositories.gsonRepository.getHabitsFlow().collect { result ->
                    when (result) {
                        is WorkResult.SuccessResult -> {
                            // If result is successful, handle the data
                            result.data?.let { tasks ->
                                tasksList.addAll(tasks.map { it.name }) // Assuming each task has a 'name' property
                            }
                        }
                        is WorkResult.ErrorResult -> {
                            // Handle the error
                        }
                        is WorkResult.LoadingResult -> {
                            // Handle loading state if needed
                        }
                        else -> {
                            // Handle other cases or unexpected results
                        }
                    }
                }

                // Join the task names into a single string
                val taskNames = tasksList.take(3).joinToString("\n")

                // Update the widget view with tasks
                views.setTextViewText(R.id.widgetTextView, taskNames)

                // Update the widget UI
                appWidgetManager.updateAppWidget(appWidgetId, views)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
