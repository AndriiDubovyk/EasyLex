package com.andriidubovyk.easylex.presentation.screen.study

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.presentation.component.DescriptionText
import com.andriidubovyk.easylex.presentation.navigation.Screen
import com.andriidubovyk.easylex.presentation.screen.study.view_model.StudyViewModel

@Composable
fun StudyScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: StudyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DescriptionText(
            descName = stringResource(R.string.total_score),
            descValue = state.totalScore.toString()
        )
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            onClick = {
                navController.navigate(Screen.PRACTICE_FLASHCARD.route)
            }
        ) {
            Text(
                text = stringResource(R.string.start),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}