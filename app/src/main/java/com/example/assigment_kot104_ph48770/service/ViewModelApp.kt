package com.example.assigment_kot104_ph48770.service

import Order
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assigment_kot104_ph48770.models.CartItem
import com.example.assigment_kot104_ph48770.models.Category
import com.example.assigment_kot104_ph48770.models.Product
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class ViewModelApp : ViewModel() {
    // Product list
    private val _listProduct = mutableStateOf<List<Product>?>(null)
    val listProduct: State<List<Product>?> = _listProduct

    fun getListProduct() {
        viewModelScope.launch {
            try {
                _listProduct.value = RetrofitInstance.api.getProducts()
            } catch (e: Exception) {
                Log.d("ProductError", e.message.toString())
            }
        }
    }

    // Category list
    private val _listCategory = mutableStateOf<List<Category>?>(null)
    val listCategory: State<List<Category>?> = _listCategory

    fun getListCategory() {
        viewModelScope.launch {
            try {
                _listCategory.value = RetrofitInstance.api.getCategories()
            } catch (e: Exception) {
                Log.d("CategoryError", e.message.toString())
            }
        }
    }

    // Cart list
    private val _cartItems = mutableStateOf<List<CartItem>>(emptyList())
    val cartItems: State<List<CartItem>> = _cartItems

    // Fetch cart items from the server
    fun fetchCartItems() {
        viewModelScope.launch {
            try {
                _cartItems.value = RetrofitInstance.api.getCartItems()
            } catch (e: Exception) {
                Log.d("CartError", e.message.toString())
            }
        }
    }

    // Add product to cart
    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                // Check if the product is already in the cart
                val existingItem = _cartItems.value.find { it.product.id == product.id }
                if (existingItem != null) {
                    // Create a new list with the updated item
                    _cartItems.value = _cartItems.value.map {
                        if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it
                    }
                } else {
                    // Add new item to cart
                    val newCartItem = CartItem(product, 1)
                    val response = RetrofitInstance.api.addToCart(newCartItem)
                    _cartItems.value = _cartItems.value + response
                }
            } catch (e: Exception) {
                Log.d("AddToCartError", e.message.toString())
            }
        }
    }

    // Update quantity of a cart item
    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            // Remove item if quantity is 0
            _cartItems.value = _cartItems.value.filter { it.product.id != cartItem.product.id }
        } else {
            // Update quantity
            _cartItems.value = _cartItems.value.map {
                if (it.product.id == cartItem.product.id) it.copy(quantity = newQuantity) else it
            }
        }
        // Optionally sync with the server (you can add a PUT or DELETE request here if needed)
    }

    // Orders list
    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: State<List<Order>> = _orders

    // Fetch orders from the server
    fun fetchOrders() {
        viewModelScope.launch {
            try {
                _orders.value = RetrofitInstance.api.getOrders()
            } catch (e: Exception) {
                Log.d("OrdersError", e.message.toString())
            }
        }
    }

    // Submit order and save to server
    fun submitOrder(cartItems: List<CartItem>, totalAmount: Double) {
        viewModelScope.launch {
            try {
                // Create a new Order object
                val order = Order(
                    orderId = UUID.randomUUID().toString(), // Generate unique order ID
                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()), // Current date
                    items = cartItems,
                    totalAmount = totalAmount,
                    status = "Delivered" // Default status
                )
                // Save order to JSON Server
                RetrofitInstance.api.saveOrder(order)
                // Clear cart
                clearCart()
                // Refresh orders
                fetchOrders()
            } catch (e: Exception) {
                Log.d("SubmitOrderError", e.message.toString())
            }
        }
    }

    // Clear all cart items
    private fun clearCart() {
        viewModelScope.launch {
            try {
                // Clear local cart only (since CartItem has no id field)
                _cartItems.value = emptyList()
                // Note: Server-side deletion requires CartItem to have an id field
                // If CartItem has an id, uncomment and adjust:
                /*
                _cartItems.value.forEach { cartItem ->
                    cartItem.id?.let { id ->
                        RetrofitInstance.api.deleteCartItem(id)
                    }
                }
                */
            } catch (e: Exception) {
                Log.d("ClearCartError", e.message.toString())
            }
        }
    }
}