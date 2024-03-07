package com.example.diary.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diary.R
import com.example.diary.data.Item
import com.example.diary.ui.AppViewModelProvider
import com.example.diary.ui.DiaryTopAppBar
import com.example.diary.ui.navigation.NavigationDestination
import com.example.diary.ui.theme.DiaryTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val weatherUiState = viewModel.weatherUiState
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var currentTab by remember { mutableStateOf(TabType.Home) }


    val navigationItemContentList = listOf(
        NavigationItemContent(
            tabType = TabType.Home,
            icon = Icons.Default.Home,
            text = stringResource(id = R.string.home)
        ),NavigationItemContent(
            tabType = TabType.List,
            icon = Icons.Default.List,
            text = stringResource(id = R.string.list)
        ),NavigationItemContent(
            tabType = TabType.Settings,
            icon = Icons.Default.Settings,
            text = stringResource(id = R.string.settings)
        )
    )

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DiaryTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
        bottomBar = {
            DiaryBottomNavigationBar(
                currentTab = currentTab,
                onTabPressed = { tabType: TabType ->
                    currentTab = tabType
                },
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(stringResource(R.string.navigation_bottom))
            )
        }
    ) { innerPadding ->
        HomeBody(
            itemList = homeUiState.itemList,
            onItemClick = navigateToItemUpdate,
            currentTab = currentTab,
            weatherUiState = weatherUiState,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeBody(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    currentTab: TabType,
    weatherUiState: WeatherUiState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxHeight()
    ) {
        if (currentTab == TabType.Home) {

            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = stringResource(id = R.string.greeting),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                    ) {
                        var text: String = ""
                        when (weatherUiState.rain) {
                            0 -> text = when (weatherUiState.sky) {
                                    1 -> "오늘은 맑은 날씨에요."
                                    3 -> "구름이 많은 날이에요."
                                    else -> "오늘은 흐린 날씨에요."
                                }
                            1 -> text = "비가 내려요."
                            2 -> text = "비 또는 눈이 내려요."
                            3 -> text = "눈이 내려요."
                            4 -> text = "소나기가 내려요."
                            else -> "날씨 정보를 표시할 수 없습니다."
                        }

                        Text(
                            text = text,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (weatherUiState.rain != -1) "${weatherUiState.temp}°" else ""
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_small)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                    ) {
                        val current = LocalDate.now()
                        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
                        val formatted = current.format(formatter)
                        Text(
                            text = "오늘은\n${formatted}입니다.",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (itemList.isEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.padding_small)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.no_item_description),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    DiaryItem(
                        item = itemList.first(),
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.padding_small))
                            .clickable { onItemClick(itemList.first().id) }
                    )
                }
            }
        } else if (currentTab == TabType.List) {
            if (itemList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_item_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                DiaryList(
                    itemList = itemList,
                    onItemClick = { onItemClick(it.id) },
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        } else {
            SettingScreen(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun DiaryBottomNavigationBar(
    currentTab: TabType,
    onTabPressed: ((TabType) -> Unit),
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        for (navItem in navigationItemContentList) {
            NavigationBarItem(
                selected = currentTab == navItem.tabType,
                onClick = { onTabPressed(navItem.tabType) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.text
                    )
                }
            )
        }
    }
}

@Composable
private fun DiaryList(
    itemList: List<Item>, onItemClick: (Item) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = itemList, key = { it.id }) { item ->
            DiaryItem(
                item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
private fun DiaryItem(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "${item.year}/${item.month}/${item.day}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = item.contents,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SettingScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier.padding(top = 50.dp))
        Text(
            text = stringResource(id = R.string.version),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

private data class NavigationItemContent(
    val tabType: TabType,
    val icon: ImageVector,
    val text: String
)

//kind of navigation bottom bar
enum class TabType {
    Home, List, Settings
}

@Preview(showBackground = true)
@Composable
fun DiaryItemPreview() {
    DiaryTheme {
        DiaryItem(
            Item(1, 2000, 1, 1, "title1", "contents1"),
        )
    }
}