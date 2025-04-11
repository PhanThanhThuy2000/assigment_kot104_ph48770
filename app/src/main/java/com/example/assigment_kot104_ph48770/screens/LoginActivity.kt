package com.example.assigment_kot104_ph48770.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.assigment_kot104_ph48770.R
import com.example.assigment_kot104_ph48770.navigations.ROUTE_NAME
//import com.example.assigment_kot104_ph48770.navigation.Destinations

// Đánh dấu đây là một hàm Composable để hiển thị màn hình đăng nhập
@OptIn(ExperimentalMaterial3Api::class) // Sử dụng API thử nghiệm của Material3
@Composable
fun LoginScreen(
    navController: NavHostController // Tham số để điều hướng giữa các màn hình trong ứng dụng
) {
    // Khai báo các biến trạng thái để theo dõi giá trị người dùng nhập
    var email by remember { mutableStateOf("") } // Lưu giá trị email, ban đầu là rỗng
    var password by remember { mutableStateOf("") } // Lưu giá trị mật khẩu, ban đầu là rỗng
    var passwordVisible by remember { mutableStateOf(false) } // Trạng thái hiển thị/ẩn mật khẩu, mặc định là ẩn
    var emailError by remember { mutableStateOf<String?>(null) } // Lưu thông báo lỗi cho email, ban đầu là null
    var passwordError by remember { mutableStateOf<String?>(null) } // Lưu thông báo lỗi cho mật khẩu, ban đầu là null

    // Hàm kiểm tra tính hợp lệ của email và mật khẩu
    fun validateInputs(): Boolean {
        // Kiểm tra email
        emailError = if (email.isBlank()) { // Nếu email rỗng
            "Email cannot be empty" // Gán thông báo lỗi
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // Nếu email không đúng định dạng
            "Invalid email format" // Gán thông báo lỗi
        } else {
            null // Không có lỗi
        }

        // Kiểm tra mật khẩu
        passwordError = if (password.isBlank()) { // Nếu mật khẩu rỗng
            "Password cannot be empty" // Gán thông báo lỗi
        } else if (password.length < 6) { // Nếu mật khẩu dưới 6 ký tự
            "Password must be at least 6 characters" // Gán thông báo lỗi
        } else {
            null // Không có lỗi
        }

        return emailError == null && passwordError == null // Trả về true nếu không có lỗi nào
    }

    // Bố cục chính của màn hình, sử dụng Box làm nền
    Box(
        modifier = Modifier
            .fillMaxSize() // Chiếm toàn bộ kích thước màn hình
            .background(Color.White) // Đặt màu nền là trắng
    ) {
        // Cột chứa toàn bộ nội dung giao diện
        Column(
            modifier = Modifier
                .fillMaxSize() // Chiếm toàn bộ kích thước của Box
                .padding(horizontal = 24.dp), // Thêm lề ngang 24dp
            horizontalAlignment = Alignment.CenterHorizontally // Căn giữa ngang các phần tử con
        ) {
            Spacer(modifier = Modifier.height(60.dp)) // Khoảng trống 60dp phía trên

            // Hàng chứa logo và hai đường kẻ ngang
            Row(
                modifier = Modifier.fillMaxWidth(), // Chiếm toàn bộ chiều rộng
                verticalAlignment = Alignment.CenterVertically // Căn giữa dọc các phần tử trong hàng
            ) {
                // Đường kẻ ngang bên trái logo
                Divider(
                    modifier = Modifier.weight(1f), // Chiếm không gian còn lại, chia đều với bên phải
                    color = Color.LightGray, // Màu xám nhạt
                    thickness = 1.dp // Độ dày 1dp
                )

                // Hộp chứa logo
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp) // Lề ngang 16dp
                        .size(80.dp) // Kích thước 80dp x 80dp
                        .clip(CircleShape) // Bo tròn thành hình tròn
                        .background(Color.White) // Nền trắng
                        .padding(16.dp), // Lề bên trong 16dp
                    contentAlignment = Alignment.Center // Căn giữa nội dung trong Box
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.group), // Tải hình ảnh từ tài nguyên drawable
                        contentDescription = "App Logo", // Mô tả nội dung cho trợ năng
                        modifier = Modifier.size(48.dp) // Kích thước logo 48dp
                    )
                }

                // Đường kẻ ngang bên phải logo
                Divider(
                    modifier = Modifier.weight(1f), // Chiếm không gian còn lại, chia đều với bên trái
                    color = Color.LightGray, // Màu xám nhạt
                    thickness = 1.dp // Độ dày 1dp
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // Khoảng trống 24dp giữa logo và tiêu đề

            // Tiêu đề "HELLO !"
            Text(
                text = "HELLO !", // Nội dung văn bản
                fontSize = 30.sp, // Cỡ chữ 30sp
                color = Color.Gray, // Màu xám
                fontWeight = FontWeight.Normal // Độ đậm bình thường
            )

            // Tiêu đề phụ "WELCOME BACK"
            Text(
                text = "WELCOME BACK", // Nội dung văn bản
                fontSize = 35.sp, // Cỡ chữ 35sp
                color = Color.DarkGray, // Màu xám đậm
                fontWeight = FontWeight.Bold // Độ đậm lớn
            )

            Spacer(modifier = Modifier.height(40.dp)) // Khoảng trống 40dp trước trường nhập liệu

            // Trường nhập email
            OutlinedTextField(
                value = email, // Giá trị hiện tại của email
                onValueChange = { email = it }, // Cập nhật giá trị khi người dùng nhập
                label = { Text("Email") }, // Nhãn hiển thị bên trong trường nhập
                modifier = Modifier.fillMaxWidth(), // Chiếm toàn bộ chiều rộng
                singleLine = true, // Chỉ cho phép nhập một dòng
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), // Bàn phím dạng email
                isError = emailError != null, // Hiển thị trạng thái lỗi nếu có
                supportingText = { // Văn bản hỗ trợ hiển thị lỗi (nếu có)
                    if (emailError != null) {
                        Text(text = emailError ?: "", color = MaterialTheme.colorScheme.error) // Hiển thị lỗi màu đỏ
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp)) // Khoảng trống 16dp giữa các trường nhập

            // Trường nhập mật khẩu
            OutlinedTextField(
                value = password, // Giá trị hiện tại của mật khẩu
                onValueChange = { password = it }, // Cập nhật giá trị khi người dùng nhập
                label = { Text("Password") }, // Nhãn hiển thị bên trong trường nhập
                modifier = Modifier.fillMaxWidth(), // Chiếm toàn bộ chiều rộng
                singleLine = true, // Chỉ cho phép nhập một dòng
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Hiển thị hoặc ẩn mật khẩu
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), // Bàn phím dạng mật khẩu
                trailingIcon = { // Biểu tượng hiển thị/ẩn mật khẩu
                    IconButton(onClick = { passwordVisible = !passwordVisible }) { // Đổi trạng thái khi nhấn
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, // Chọn biểu tượng dựa trên trạng thái
                            contentDescription = if (passwordVisible) "Hide password" else "Show password" // Mô tả trợ năng
                        )
                    }
                },
                isError = passwordError != null, // Hiển thị trạng thái lỗi nếu có
                supportingText = { // Văn bản hỗ trợ hiển thị lỗi (nếu có)
                    if (passwordError != null) {
                        Text(text = passwordError ?: "", color = MaterialTheme.colorScheme.error) // Hiển thị lỗi màu đỏ
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp)) // Khoảng trống 8dp trước văn bản "Forgot Password"

            // Văn bản "Forgot Password" với khả năng nhấn
            Text(
                text = "Forgot Password", // Nội dung văn bản
                modifier = Modifier
                    .align(Alignment.End) // Căn phải
                    .clickable { /* Xử lý quên mật khẩu */ }, // Có thể nhấn (chưa xử lý logic)
                color = Color.Gray, // Màu xám
                fontSize = 14.sp, // Cỡ chữ 14sp
                fontWeight = FontWeight.Medium // Độ đậm trung bình
            )

            Spacer(modifier = Modifier.height(24.dp)) // Khoảng trống 24dp trước nút đăng nhập

            // Nút "Log in"
            Button(
                onClick = {
                    if (validateInputs()) { // Kiểm tra tính hợp lệ trước khi thực hiện
                        navController.navigate(ROUTE_NAME.home.name)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth() // Chiếm toàn bộ chiều rộng
                    .height(56.dp), // Chiều cao 56dp
                shape = RoundedCornerShape(8.dp), // Bo góc 8dp
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black) // Màu nền đen
            ) {
                Text(
                    text = "Log in", // Nội dung văn bản
                    fontSize = 20.sp, // Cỡ chữ 20sp
                    color = Color.White, // Màu trắng
                    fontWeight = FontWeight.Medium // Độ đậm trung bình
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // Khoảng trống 16dp giữa các nút

            // Nút "SIGN UP"
            Button(
                onClick = {
                    navController.navigate(ROUTE_NAME.signup.name)


                }, // Điều hướng đến màn hình đăng ký
                modifier = Modifier
                    .fillMaxWidth() // Chiếm toàn bộ chiều rộng
                    .height(56.dp), // Chiều cao 56dp
                shape = RoundedCornerShape(8.dp), // Bo góc 8dp
                colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Màu nền trắng
            ) {
                Text(
                    text = "SIGN UP", // Nội dung văn bản
                    fontSize = 20.sp, // Cỡ chữ 20sp
                    color = Color.Black, // Màu đen
                    fontWeight = FontWeight.Bold // Độ đậm lớn
                )
            }
        }
    }
}