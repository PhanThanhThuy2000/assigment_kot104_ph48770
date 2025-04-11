package com.example.assigment_kot104_ph48770.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.assigment_kot104_ph48770.service.ViewModelApp

@Composable
fun CategoryProductsScreen(
    categoryName: String,
    innerPadding: PaddingValues,
    navController: NavController,
    viewModelApp: ViewModelApp = viewModel()
) {
    // Observe ViewModel states
    val categoryProducts by viewModelApp.categoryProducts
    val isLoading by viewModelApp.isLoading
    val errorMessage by viewModelApp.errorMessage

    // Fetch products for the category
    LaunchedEffect(categoryName) {
        viewModelApp.getProductsByCategory(categoryName)
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Products in $categoryName",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            errorMessage != null -> {
//                Text(
//                    text = errorMessage,
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
            }
            categoryProducts.isNullOrEmpty() -> {
                Text(
                    text = "No products available in this category",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    itemsIndexed(categoryProducts!!) { _, item ->
                        ProductItem(
                            image = item.image,
                            name = item.name,
                            price = item.price,
                            productId = item.id,
                                    navController = navController
                        )
                    }
                }
            }
        }
    }
}