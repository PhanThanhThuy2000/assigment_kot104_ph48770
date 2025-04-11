//package com.example.assigment_kot104_ph48770.screens
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.horizontalScroll
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.itemsIndexed
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.outlined.Favorite
//import androidx.compose.material.icons.outlined.Home
//import androidx.compose.material.icons.outlined.Notifications
//import androidx.compose.material.icons.outlined.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.alpha
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.AnnotatedString
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.graphics.toColorInt
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
//import com.example.assigment_kot104_ph48770.R
//import com.example.assigment_kot104_ph48770.models.Product
//import com.example.assigment_kot104_ph48770.service.ViewModelApp
//
//
//@Composable
//fun CategoryProductsScreen(
//    categoryName: String,
//    innerPadding: PaddingValues,
//    navHostController: NavController,
//    navController: NavController,
//    viewModelApp: ViewModelApp = viewModel()
//) {
//    LaunchedEffect(Unit) {
//        viewModelApp.getListProduct() // Ensure products are loaded
//    }
//    val listProductState by remember { viewModelApp.listProduct }
//    val listProduct = listProductState ?: emptyList() // Default to empty if null
//
//    // Filter products by category name (assuming Product has a category field)
//    val filteredProducts = listProduct.filter { it.name == categoryName } // Adjust this logic based on your data model
//
//    Column(
//        modifier = Modifier
//            .padding(innerPadding)
//            .padding(top = 10.dp, end = 15.dp, start = 15.dp)
//    ) {
//        Text(
//            text = "Products in $categoryName",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//            modifier = Modifier.padding(bottom = 10.dp)
//        )
//
//        when {
//            listProduct == null -> Text(
//                text = "Loading products...",
//                fontSize = 16.sp,
//                color = Color.Blue,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//            filteredProducts.isEmpty() -> Text(
//                text = "No products available in this category",
//                fontSize = 16.sp,
//                color = Color.Red,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//            else -> {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(15.dp),
//                    verticalArrangement = Arrangement.spacedBy(15.dp)
//                ) {
//                    itemsIndexed(filteredProducts) { _, item ->
//                        ProductItem(
//                            image = item.image,
//                            name = item.name,
//                            price = item.price.toDouble(),
//                            navController = navController
//                        )
//                    }
//                }
//            }
//        }
//    }
//}