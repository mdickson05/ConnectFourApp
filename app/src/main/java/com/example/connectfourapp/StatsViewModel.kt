package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StatsViewModel : ViewModel() {
    private val firebaseRef = FirebaseDatabase.getInstance().getReference()
    var playerStats by mutableStateOf<List<PlayerStats>>(emptyList())
        // Use mutableStateOf to create observable state
        private set // used in updateStats()

    init {
        updateStats() // When the StatsViewModel is created, updateStats will run
    }

    private fun updateStats() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val statsList = mutableListOf<PlayerStats>() // new list to store stats
                    for (statsSnap in snapshot.children) {
                        val statsHolder = statsSnap.getValue(StatsHolder::class.java)
                        statsHolder?.let{
                            // if null, does not operate, else adds StatsHolder values to the list
                            statsList.add(statsHolder.playerOneSP)
                            statsList.add(statsHolder.ai)
                            statsList.add(statsHolder.playerOneMP)
                            statsList.add(statsHolder.playerTwo)
                        }
                    }
                    playerStats = statsList
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Does nothing, for now
            }
        })
    }
}