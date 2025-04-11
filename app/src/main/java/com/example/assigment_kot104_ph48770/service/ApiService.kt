package com.example.assigment_kot104_ph48770.service

import Order
import com.example.assigment_kot104_ph48770.models.CartItem
import com.example.assigment_kot104_ph48770.models.Category
import com.example.assigment_kot104_ph48770.models.Product
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("product")
    suspend fun getProducts(): List<Product>

    @GET("category")
    suspend fun getCategories(): List<Category>

    @GET("category/{categoryName}/products")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): List<Product>

    // Fetch cart items from JSON Server
    @GET("cart")
    suspend fun getCartItems(): List<CartItem>

    // Add a cart item to JSON Server
    @POST("cart")
    suspend fun addToCart(@Body cartItem: CartItem): CartItem

    // Update a cart item on JSON Server
    @PUT("cart/{id}")
    suspend fun updateCartItem(@Path("id") id: Int, @Body cartItem: CartItem): CartItem

    // Delete a cart item from JSON Server
//    @DELETE("cart/{id}")
//    suspend fun deleteCartItem(@Path("id") id: Int)
    @DELETE("cart/{id}")
    suspend fun deleteCartItem(@Path("id") id: Int): Response<Unit>
    // Fetch orders from JSON Server (optional, for persistence)
    @GET("orders")
    suspend fun getOrders(): List<Order>

    // Save an order to JSON Server (optional, for persistence)
    @POST("orders")
    suspend fun saveOrder(@Body order: Order): Order
}

// Create Retrofit object
object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.4:3000/" // Ensure this matches your JSON Server URL
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}