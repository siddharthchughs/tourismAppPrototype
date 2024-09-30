package com.example.tipcalculator.tipscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tipcalculator.R
import com.example.tipcalculator.components.CardDisplayTotalPerPerson
import com.example.tipcalculator.components.DisplayCountOfPersonText
import com.example.tipcalculator.components.SplitLabelText
import com.example.tipcalculator.components.SplittingIntoLessPersonIcon
import com.example.tipcalculator.components.SplittingIntoMorePersonIcon
import com.example.tipcalculator.components.TipLabelText
import com.example.tipcalculator.components.TotalAmountTextField
import com.example.tipcalculator.components.TotalTipDisplayText

@Composable
fun TipCalculatorScreen(modifier: Modifier = Modifier) {
    val tipCalculatorViewModel: TipCalculatorViewModel = hiltViewModel()
    val tipCalculatorUIState =
        tipCalculatorViewModel.tipUIState.collectAsStateWithLifecycle(initialValue = TipCalculatorUIState.Loading)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TipCalcualtorToolAppBar()
        TipCalculatorStructure(
            tipCalculatorUIState = tipCalculatorUIState.value,
            totalPerPerson = tipCalculatorViewModel.totalPerPerson.value,
            totalBillPerPerson = tipCalculatorViewModel::totalBillPerPerson,
            totalBillText = tipCalculatorViewModel.totalBillText.value,
            onAmountChange = tipCalculatorViewModel::onTotalBillChange,
            splitAsPerCount = tipCalculatorViewModel.splitAsPerCount.value,
            increaseCount = tipCalculatorViewModel::increaseCount,
            subtractFromCount = tipCalculatorViewModel::subtractFromCount,
            sliderPositionState = tipCalculatorViewModel.sliderPositionState.value,
            onSliderRangeUpdate = tipCalculatorViewModel::onSliderRangeUpdate,
            tipPercentage = tipCalculatorViewModel.tipPercentage.value,
            onTipPercentage = tipCalculatorViewModel::onTipPercentage,
            tipCalculateText = tipCalculatorViewModel.tipCalculateText.value,
            tipCalculation = tipCalculatorViewModel::tipCalculation

        )
    }
}

@Composable
fun TipCalculatorStructure(
    tipCalculatorUIState: TipCalculatorUIState,
    totalPerPerson: Double,
    totalBillPerPerson: () -> Unit,
    totalBillText: String,
    onAmountChange: (String) -> Unit,
    splitAsPerCount: Int,
    increaseCount: () -> Unit,
    subtractFromCount: () -> Unit,
    sliderPositionState: Float,
    onSliderRangeUpdate: (Float) -> Unit,
    tipPercentage: Int,
    onTipPercentage: () -> Unit,
    tipCalculateText: Double,
    tipCalculation: () -> Unit
) {
    when (tipCalculatorUIState) {

        TipCalculatorUIState.Loaded -> {
            totalBillPerPerson()
            CardDisplayTotalPerPerson(
                totalAmountPerPersom = totalPerPerson
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
            BillForm(
                totalBillText = totalBillText,
                onAmountChange = onAmountChange,
                splitAsPerCount = splitAsPerCount,
                increaseCount = increaseCount,
                subtractFromCount = subtractFromCount,
                sliderPositionState = sliderPositionState,
                onSliderRangeUpdate = onSliderRangeUpdate,
                tipPercentage = tipPercentage,
                onTipPercentage = onTipPercentage,
                tipCalculateText = tipCalculateText,
                tipCalculation = tipCalculation
            )
        }

        TipCalculatorUIState.Loading -> {
            ProgressBar()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalcualtorToolAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = stringResource(R.string.title_label),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        navigationIcon = {},
        actions = {}
    )
    Divider(
        modifier = modifier
            .fillMaxWidth(),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    totalBillText: String,
    onAmountChange: (String) -> Unit,
    splitAsPerCount: Int,
    increaseCount: () -> Unit,
    subtractFromCount: () -> Unit,
    sliderPositionState: Float,
    onSliderRangeUpdate: (Float) -> Unit,
    tipPercentage: Int,
    onTipPercentage: () -> Unit,
    tipCalculateText: Double,
    tipCalculation: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = modifier.padding(horizontal = 8.dp, vertical = 10.dp)) {
            TotalBillTextInput(
                totalBillText = totalBillText,
                onAmountChange = onAmountChange,
                splitAsPerCount = splitAsPerCount,
                increaseCount = increaseCount,
                subtractFromCount = subtractFromCount,
                sliderPositionState = sliderPositionState,
                onSliderRangeUpdate = onSliderRangeUpdate,
                tipPercentage = tipPercentage,
                onTipPercentage = onTipPercentage,
                tipCalculateText = tipCalculateText,
                tipCalculation = tipCalculation
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
            )
        }
    }
}

@Composable
fun TotalBillTextInput(
    modifier: Modifier = Modifier,
    totalBillText: String,
    onAmountChange: (String) -> Unit,
    splitAsPerCount: Int,
    increaseCount: () -> Unit,
    subtractFromCount: () -> Unit,
    sliderPositionState: Float,
    onSliderRangeUpdate: (Float) -> Unit,
    tipPercentage: Int,
    onTipPercentage: () -> Unit,
    tipCalculateText: Double,
    tipCalculation: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    TotalAmountTextField(
        totalBillText = totalBillText,
        onAmountChange = {
            onAmountChange(it)
        },
        isSingleLine = true,
        maxLine = 1,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary
        ),
        label = stringResource(R.string.hint_text_total_bill),
        enabled = true
    )

    if (totalBillText.isNotBlank()) {
        Column(
            modifier = modifier
                .padding(8.dp)
        ) {
            SplitBillDisplay(
                splitAsPerCount = splitAsPerCount,
                increaseCount = increaseCount,
                subtractFromCount = subtractFromCount
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
            )
            tipCalculation()
            TotalTipOffered(
                tipCalculateText = tipCalculateText,
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
            )
            TipPercentage(
                tipPercentage = tipPercentage,
                onTipPercentage = onTipPercentage
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
            )
            TipSlider(
                sliderPositionState = sliderPositionState,
                onSliderRangeUpdate = onSliderRangeUpdate
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
            )
        }
    }
}

@Composable
fun SplitBillDisplay(
    splitAsPerCount: Int,
    increaseCount: () -> Unit,
    subtractFromCount: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(2.dp)
            .wrapContentWidth()
            .background(Color.White)
    ) {
        SplitLabelText(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(0.2f)
        )
        Spacer(
            modifier = Modifier
                .width(120.dp)
                .wrapContentHeight()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(0.2f)
                .padding(horizontal = 3.dp)
        ) {
            SplittingIntoLessPersonIcon(
                subtractFromCount = subtractFromCount,
                modifier = Modifier
                    .weight(0.4f)
            )
            DisplayCountOfPersonText(
                no_of_split = splitAsPerCount,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            SplittingIntoMorePersonIcon(
                increasePerPerson = increaseCount,
                modifier = Modifier
                    .weight(0.4f)
            )
        }
    }
}

@Composable
fun TotalTipOffered(
    tipCalculateText: Double,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        TipLabelText(
            modifier = modifier
                .padding(vertical = 8.dp)
                .wrapContentHeight()
        )
        TotalTipDisplayText(
            tip = "$$tipCalculateText",
            modifier = modifier
                .wrapContentHeight()
        )
    }
}

@Composable
fun TipPercentage(
    tipPercentage: Int,
    onTipPercentage: () -> Unit
) {
    onTipPercentage()
    TotalTipDisplayText(
        tip = "$tipPercentage %",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun TipSlider(
    sliderPositionState: Float,
    onSliderRangeUpdate: (Float) -> Unit
) {
    val position = remember { mutableFloatStateOf(sliderPositionState) }
    Slider(
        value = position.floatValue,
        onValueChange = { newVal ->
            position.floatValue = newVal
            onSliderRangeUpdate(position.floatValue)
        },
        modifier = Modifier
            .padding(horizontal = 8.dp),
        steps = 5,
        onValueChangeFinished = {}
    )
}

@Composable
fun TotalTipCalculation(

) {

}


@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Spacer(
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Min)
        )
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .progressSemantics()
                .background(Color.Magenta)
        )
        Spacer(
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Min)
        )
    }
}
