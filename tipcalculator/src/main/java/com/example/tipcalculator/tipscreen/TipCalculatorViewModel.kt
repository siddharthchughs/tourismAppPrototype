package com.example.tipcalculator.tipscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipcalculator.Utility.calculateTipPercentage
import com.example.tipcalculator.Utility.calculateTotalPerPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TipCalculatorUIState {
    data object Loading : TipCalculatorUIState
    data object Loaded : TipCalculatorUIState
}

@HiltViewModel
class TipCalculatorViewModel @Inject constructor() : ViewModel() {

    val totalBillText = mutableStateOf("")
    val splitAsPerCount = mutableStateOf(1)
    val range = IntRange(start = 1, endInclusive = 10)
    val sliderPositionState = mutableStateOf(0f)
    val tipPercentage = mutableStateOf(0)
    val tipCalculateText = mutableStateOf(0.0)
    val totalPerPerson = mutableStateOf(0.0)

    val tipUIState: Flow<TipCalculatorUIState> = flowOf(
        transform()
    )

    private fun transform(): TipCalculatorUIState {
        viewModelScope.launch {
            delay(1_000)
        }
        return TipCalculatorUIState.Loaded
    }

    fun increaseCount() {
        if (splitAsPerCount.value < range.last)
            splitAsPerCount.value += 1
    }

    fun subtractFromCount() {
        splitAsPerCount.value = if (splitAsPerCount.value > 1)
            splitAsPerCount.value.minus(1)
        else
            1
    }

    fun onTotalBillChange(totalBill: String) {
        totalBillText.value = totalBill
    }

    fun onSliderRangeUpdate(sliderPosition: Float) {
        sliderPositionState.value = sliderPosition
    }

    fun onTipPercentage() {
        tipPercentage.value = (sliderPositionState.value * 100).toInt()
    }

    fun tipCalculation() {
        tipCalculateText.value = calculateTipPercentage(
            totalBill = totalBillText.value.toDouble(),
            tipPercentage = tipPercentage.value
        )
    }

    fun totalBillPerPerson(){
       totalPerPerson.value = calculateTotalPerPerson(
           totalBill = if(totalBillText.value.isNotEmpty()) totalBillText.value.toDouble() else 0.0,
           tipPercentage = tipCalculateText.value.toInt(),
           split = splitAsPerCount.value
       )
    }
}
