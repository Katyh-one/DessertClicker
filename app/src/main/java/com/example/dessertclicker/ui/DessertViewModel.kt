package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.data.DessertUIState
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    //    dessert clicker ui state
    private val _uiState = MutableStateFlow(DessertUIState())
    val uiState: StateFlow<DessertUIState> = _uiState.asStateFlow()

    private val desserts = Datasource.dessertList

// Update the revenue and the total desserts sold
//    revenue += currentDessertPrice
//    dessertsSold++

    fun updateRevenueAndDessertsSold() {
        _uiState.update { currentState ->
            currentState.copy(
                revenue = uiState.value.revenue + uiState.value.currentDessertPrice,
                dessertsSold = uiState.value.dessertsSold+ 1
            )
        }
    }

    /**
     * Determine which dessert to show.
     */
    private fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }

    fun showNextDessert() {
        val dessertToShow = determineDessertToShow(desserts, uiState.value.dessertsSold)
        _uiState.update { currentState ->
            currentState.copy(
                currentDessertImageId = dessertToShow.imageId,
                currentDessertPrice = dessertToShow.price
            )
        }
    }
    // Show the next dessert
//    val dessertToShow = determineDessertToShow(desserts, dessertsSold)
//    currentDessertImageId = dessertToShow.imageId
//    currentDessertPrice = dessertToShow.price


}