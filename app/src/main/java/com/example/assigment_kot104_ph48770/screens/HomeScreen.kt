package com.example.assigment_kot104_ph48770.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.assigment_kot104_ph48770.R
import com.example.assigment_kot104_ph48770.models.Product
import com.example.assigment_kot104_ph48770.service.ViewModelApp
import java.text.Normalizer
enum class ROUTE_HOME_SCREEN {
    Home,
    Favorite,
    Notification,
    Profile,
    CategoryProducts
}
fun String.removeDiacritics(): String {
    val normalized = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
}
class Category(val image: String, val name: String)

@Composable
fun HomeScreen(
    innerPadding: PaddingValues = PaddingValues(),
    navController: NavController,
    viewModelApp: ViewModelApp = viewModel(),
    isSearchBarVisible: Boolean,
    onSearchBarToggle: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModelApp.getListProduct()
        viewModelApp.getListCategory()
    }
    val listProductState by remember { viewModelApp.listProduct }
    val listProduct = listProductState ?: emptyList()
    val listCategoryState by remember { viewModelApp.listCategory }
    val listCategory = listCategoryState ?: emptyList()

    // Filtered product list based on search query (with diacritics removed)
    val filteredProducts = listProduct.filter {
        it.name.removeDiacritics().contains(searchQuery.removeDiacritics(), ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp, end = 15.dp, start = 15.dp)
            .fillMaxSize()
    ) {
        // Search Bar (only shown when isSearchBarVisible is true)
        if (isSearchBarVisible) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                placeholder = { Text("Search products...") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "Search Icon",
                        modifier = Modifier.clickable { onSearchBarToggle(false) }
                    )
                },
                shape = RoundedCornerShape(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        when {
            listCategory.isEmpty() -> Text(
                text = "Loading categories...",
                fontSize = 16.sp,
                color = Color.Blue,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            else -> Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                listCategory.forEach { item ->
                    CategoryItem(
                        image = item.image,
                        name = item.name,
                        navController = navController
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        when {
            filteredProducts.isEmpty() -> Text(
                text = if (searchQuery.isEmpty()) "Loading products..." else "No products found",
                fontSize = 16.sp,
                color = if (searchQuery.isEmpty()) Color.Blue else Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    itemsIndexed(filteredProducts) { _, item ->
                        ProductItem(
                            image = item.image,
                            name = item.name,
                            price = item.price.toDouble(),
                            productId = item.id,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(image: String, name: String, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(end = 0.dp)
            .clickable { navController.navigate("category/$name") },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .size(44.dp)
                .shadow(elevation = 2.dp, RoundedCornerShape(14.dp))
                .background(color = Color("#F5F5F5".toColorInt())),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.image3),
                error = painterResource(R.drawable.image3)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = name,
            color = Color("#999999".toColorInt()),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light))
        )
    }
}

@Composable
fun ProductItem(image: String, name: String, price: Double, productId: String, navController: NavController) {
    Column(
        modifier = Modifier
            .width(165.dp)
            .height(253.dp)
            .clickable { navController.navigate("detail/$productId") },
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.image3),
                error = painterResource(R.drawable.image3)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, end = 15.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Row {}
                Row(
                    modifier = Modifier
                        .size(30.dp)
                        .shadow(elevation = 2.dp, RoundedCornerShape(6.dp))
                        .background(color = Color("#95a5a6".toColorInt()))
                        .alpha(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.shopping_bag),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
        Text(
            text = name,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light))
        )
        Text(
            text = "\$ $price",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.nunitosans_7pt_condensed_light))
        )
    }
}

@Composable
fun FurnitureApp(navHostController: NavController) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavigationItem(ROUTE_HOME_SCREEN.Home.name, Icons.Default.Home, Icons.Outlined.Home),
        BottomNavigationItem(ROUTE_HOME_SCREEN.Favorite.name, Icons.Default.Favorite, Icons.Outlined.Favorite),
        BottomNavigationItem("Notification", Icons.Default.Notifications, Icons.Outlined.Notifications),
        BottomNavigationItem(ROUTE_HOME_SCREEN.Profile.name, Icons.Default.Person, Icons.Outlined.Person)
    )
    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }

    // Search state managed at FurnitureApp level
    var isSearchBarVisible by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navController = navController,
                    navHostController = navHostController,
                    onSearchClick = { isSearchBarVisible = !isSearchBarVisible } // Toggle search bar
                )
            },
            bottomBar = {
                BottomNavigationBar(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onItemSelected = { index ->
                        selectedItemIndex = index
                        navController.navigate(items[index].title)
                    }
                )
            }
        ) { innerPadding ->
            NavigationGraph(
                navHostController = navHostController,
                navController = navController,
                innerPadding = innerPadding,
                isSearchBarVisible = isSearchBarVisible,
                onSearchBarToggle = { isSearchBarVisible = it },
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavHostController,
    navHostController: NavController,
    onSearchClick: () -> Unit // Callback to toggle search bar
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Home"
    val homeTitle = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight(400),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
        ) {
            append("Make home\n")
        }
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily(Font(R.font.gelasio_bold))
            )
        ) {
            append("BEAUTIFUL")
        }
    }

    val title: Any = when (currentRoute) {
        "Home" -> homeTitle
        "Favorite" -> "Favorite"
        "Notification" -> "Notification"
        "Profile" -> "Profile"
        else -> "Furniture App"
    }
    androidx.compose.material3.TopAppBar(
        title = {
            if (title is AnnotatedString) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else if (title is String) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = FontFamily(Font(R.font.merriweather_regular))
                )
            }
        },
        actions = {
            IconButton(onClick = { navHostController.navigate("cart") }) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onSearchClick) { // Use TopAppBar's search icon
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color("#ffffff".toColorInt())
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndex == index) item.selectIcon else item.unselectItem,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = android.R.color.black),
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navHostController: NavController,
    navController: NavHostController,
    innerPadding: PaddingValues,
    isSearchBarVisible: Boolean,
    onSearchBarToggle: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    NavHost(
        navController,
        startDestination = ROUTE_HOME_SCREEN.Home.name,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(ROUTE_HOME_SCREEN.Home.name) {
            HomeScreen(
                innerPadding = innerPadding,
                navController = navHostController,
                isSearchBarVisible = isSearchBarVisible,
                onSearchBarToggle = onSearchBarToggle,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )
        }
        composable(ROUTE_HOME_SCREEN.Favorite.name) { FavoriteScreen(innerPadding) }
        composable(ROUTE_HOME_SCREEN.Notification.name) { NotificationScreen(innerPadding) }
        composable(ROUTE_HOME_SCREEN.Profile.name) { AccountScreenControl(innerPadding, navHostController) }
        composable(
            route = "category/{categoryName}"
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryProductsScreen(
                categoryName = categoryName,
                innerPadding = innerPadding,
                navController = navHostController
            )
        }
        composable(
            route = "productDetail/{productId}"
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(productId, navHostController)
        }
        composable("cart") { CartScreen(innerPadding, navHostController) } // Assuming you have a CartScreen
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectIcon: ImageVector,
    var unselectItem: ImageVector
)