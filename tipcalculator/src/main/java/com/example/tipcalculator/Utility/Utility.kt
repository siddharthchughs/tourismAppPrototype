package com.example.tipcalculator.Utility

fun calculateTipPercentage(
    totalBill: Double,
    tipPercentage: Int
): Double {
    return if (totalBill > 0 && totalBill.toString().isNotEmpty())
        (totalBill * tipPercentage) / 100 else 0.0
}

fun calculateTotalPerPerson(
    totalBill: Double,
    tipPercentage:Int,
    split:Int
):Double{
    val bill = calculateTipPercentage(totalBill = totalBill,tipPercentage = tipPercentage)+totalBill
    return bill/split
}