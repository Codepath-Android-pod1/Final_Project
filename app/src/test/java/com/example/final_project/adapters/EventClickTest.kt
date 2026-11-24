package com.example.final_project.adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verifyNoInteractions

class EventClickTest {
    private fun itemView(): View {
        val view = mock(View::class.java)
        for (id in listOf(R.id.tvTitle, R.id.tvDate, R.id.tvDayTime, R.id.tvLocation)) {
            `when`(view.findViewById<TextView>(id)).thenReturn(mock(TextView::class.java))
        }
        return view
    }

    @Test
    fun ticketmasterClickWithoutAdapterPositionDoesNotNavigate() {
        val context = mock(Context::class.java)
        val adapter = EventAdapter(context, mutableListOf())
        val holder = adapter.ViewHolder(itemView())
        assertEquals(RecyclerView.NO_POSITION, holder.absoluteAdapterPosition)

        holder.onClick(null)

        verifyNoInteractions(context)
    }

    @Test
    fun parseClickWithoutAdapterPositionDoesNotNavigate() {
        val context = mock(Context::class.java)
        val adapter = ParseEventAdapter(context, mutableListOf())
        val holder = adapter.ViewHolder(itemView())
        assertEquals(RecyclerView.NO_POSITION, holder.absoluteAdapterPosition)

        holder.onClick(null)

        verifyNoInteractions(context)
    }
}
