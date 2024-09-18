package com.example.connectfourapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StatsViewModel(
    private val gameViewModel: GameViewModel
) : ViewModel() {
    private val playerOneSPRef = FirebaseDatabase.getInstance().getReference("Player 1 (SP)")
    private val playerOneMPRef = FirebaseDatabase.getInstance().getReference("Player 1 (MP)")
    private val playerTwoRef = FirebaseDatabase.getInstance().getReference("Player 2")
    private val aiRef = FirebaseDatabase.getInstance().getReference("AI")

    private var _toastMessage by mutableStateOf<String?>(null)
    val toastMessage: String? get() = _toastMessage

    var playerStats by mutableStateOf<List<PlayerStats>>(emptyList())
        // Use mutableStateOf to create observable state
        private set // used in updateStats()

    fun getStatsScreen()
    {
        playerStats = emptyList()
        databaseExists(playerOneSPRef, playerOneMPRef, playerTwoRef, aiRef) { allExist ->
            if (allExist) {
                // If all references exist, fetchStats()
                fetchStats()

            } else {
                // If any of the references don't exist, create a new database
                gameViewModel.createDatabase {
                    fetchStats()
                }
            }
        }
    }


    private fun fetchStats() {
        // Fetch playerOneSP stats
        playerOneSPRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val playerOneSPStats = snapshot.getValue(PlayerStats::class.java)
                playerOneSPStats?.let {
                    addPlayerStats(it)
                }
            }
        }.addOnFailureListener {
            _toastMessage = "Failed to fetch Player 1 (SP) stats: ${it.message}"
        }

        // Fetch playerOneMP stats
        playerOneMPRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val playerOneMPStats = snapshot.getValue(PlayerStats::class.java)
                playerOneMPStats?.let {
                    addPlayerStats(it)
                }
            }
        }.addOnFailureListener {
            _toastMessage = "Failed to fetch Player 1 (MP) stats: ${it.message}"
        }

        // Fetch playerTwo stats
        playerTwoRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val playerTwoStats = snapshot.getValue(PlayerStats::class.java)
                playerTwoStats?.let {
                    addPlayerStats(it)
                }
            }
        }.addOnFailureListener {
            _toastMessage = "Failed to fetch Player 2 stats: ${it.message}"
        }

        // Fetch AI stats
        aiRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val aiStats = snapshot.getValue(PlayerStats::class.java)
                aiStats?.let {
                    addPlayerStats(it)
                }
            }
        }.addOnFailureListener {
            _toastMessage = "Failed to fetch AI stats: ${it.message}"
        }
    }

    private fun databaseExists(vararg refs : DatabaseReference, onComplete: (Boolean) -> Unit)
    {
        var allExist = true
        var completedChecks = 0

        for (ref in refs) {
            ref.get().addOnSuccessListener { dataSnapshot ->
                if (!dataSnapshot.exists()) {
                    allExist = false
                }
                completedChecks++
                if (completedChecks == refs.size) {
                    // Once all checks are done, invoke the callback
                    onComplete(allExist)
                }
            }.addOnFailureListener {
                // Handle errors in reading data, possibly a network issue
                _toastMessage = "Error with checking if database exists!"
                allExist = false
                completedChecks++
                if (completedChecks == refs.size) {
                    onComplete(false)
                }
            }
        }
    }

    // Helper function to add player stats to the list
    private fun addPlayerStats(stats: PlayerStats) {
        // Create a new list by adding the new stats to the current playerStats list
        playerStats = playerStats.toMutableList().apply {
            add(stats) // Add new player stats
        }
    }

    fun clearToastMessage() {
        _toastMessage = null
    }
}