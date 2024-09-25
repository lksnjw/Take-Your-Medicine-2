package com.example.TakeYourMedicine

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.TakeYourMedicine.model.Repositories
import com.example.TakeYourMedicine.model.Result // Adjust this based on your actual result class
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.widget_layout)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val taskList = mutableListOf<String>()
                Repositories.repository.getHabitsFlow().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            // Assuming 'data' is a list of tasks or habits
                            result.data?.let { tasks ->
                                taskList.addAll(tasks.map { it.name }) // Make sure 'name' is a property of your task/habit class
                            }
                        }
                        is Result.Error -> {
                            // Handle the error
                        }
                        is Result.Loading -> {
                            // Handle loading state if needed
                        }
                        else -> {
                            // Handle any unexpected results
                        }
                    }
                }

                // Join the task names into a single string to display on the widget
                val taskNames = taskList.take(3).joinToString("\n")

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
