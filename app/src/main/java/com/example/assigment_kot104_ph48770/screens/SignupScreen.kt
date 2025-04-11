package com.example.assigment_kot104_ph48770.screens

import androidx.compose.foundation.Image // Thư viện để hiển thị hình ảnh trong Compose
import androidx.compose.foundation.background // Thư viện để đặt màu nền
import androidx.compose.foundation.layout.* // Thư viện bố cục (Column, Row, Spacer, v.v.)
import androidx.compose.foundation.shape.CircleShape // Hình dạng tròn cho các thành phần giao diện
import androidx.compose.material.* // Thư viện Material Design (phiên bản cũ hơn Material3)
import androidx.compose.material.icons.Icons // Thư viện biểu tượng mặc định
import androidx.compose.material.icons.filled.Visibility // Biểu tượng mắt để hiển thị mật khẩu
import androidx.compose.material.icons.filled.VisibilityOff // Biểu tượng mắt gạch chéo để ẩn mật khẩu
import androidx.compose.material3.Divider // Đường kẻ ngang từ Material3
import androidx.compose.runtime.* // Thư viện để quản lý trạng thái (remember, mutableStateOf)
import androidx.compose.ui.Alignment // Căn chỉnh các phần tử giao diện
import androidx.compose.ui.Modifier // Công cụ tùy chỉnh giao diện (padding, size, v.v.)
import androidx.compose.ui.draw.clip // Cắt hình dạng cho phần tử giao diện
import androidx.compose.ui.graphics.Color // Thư viện màu sắc
import androidx.compose.ui.res.painterResource // Tải tài nguyên hình ảnh từ drawable
import androidx.compose.ui.text.font.FontWeight // Độ đậm của văn bản
import androidx.compose.ui.text.input.PasswordVisualTransformation // Ẩn mật khẩu dưới dạng dấu chấm
import androidx.compose.ui.text.input.VisualTransformation // Hiển thị văn bản bình thường
import androidx.compose.ui.tooling.preview.Preview // Xem trước giao diện trong IDE
import androidx.compose.ui.unit.dp // Đơn vị đo lường dp
import androidx.compose.ui.unit.sp // Đơn vị đo lường sp cho kích thước chữ
import androidx.navigation.NavHostController // Điều hướng giữa các màn hình
import androidx.navigation.compose.rememberNavController // Tạo NavController giả lập cho bản xem trước
import com.example.assigment_kot104_ph48770.R
import com.example.assigment_kot104_ph48770.navigations.ROUTE_NAME
//import com.example.assigment_kot104_ph48770.navigation.Destinations // Danh sách các đích đến trong điều hướng
import com.example.assigment_kot104_ph48770.ui.theme.Assigment_KOT104_PH48770Theme // Theme tùy chỉnh của ứng dụng

// Định nghĩa hàm Composable cho màn hình đăng ký
@Composable
fun SignupScreen(navController: NavHostController) { // Tham số điều hướng giữa các màn hình
    // Khai báo các biến trạng thái để lưu dữ liệu người dùng nhập
    var name by remember { mutableStateOf("") } // Lưu tên người dùng, mặc định rỗng
    var email by remember { mutableStateOf("") } // Lưu email, mặc định rỗng
    var password by remember { mutableStateOf("") } // Lưu mật khẩu, mặc định rỗng
    var confirmPassword by remember { mutableStateOf("") } // Lưu xác nhận mật khẩu, mặc định rỗng
    var passwordVisible by remember { mutableStateOf(false) } // Trạng thái hiển thị/ẩn mật khẩu, mặc định ẩn
    var confirmPasswordVisible by remember { mutableStateOf(false) } // Trạng thái hiển thị/ẩn xác nhận mật khẩu, mặc định ẩn

    // Cột chứa toàn bộ giao diện màn hình đăng ký
    Column(
        modifier = Modifier
            .fillMaxSize() // Chiếm toàn bộ kích thước màn hình
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp), // Lề: trên 32dp, dưới 16dp, trái/phải 16dp
    ) {
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

        // Tiêu đề "WELCOME"
        Text(
            text = "WELCOME", // Nội dung văn bản
            style = MaterialTheme.typography.h4, // Kiểu chữ tiêu đề cấp 4 từ MaterialTheme
            fontSize = 28.sp, // Cỡ chữ 28sp
            fontWeight = FontWeight.Bold, // Độ đậm lớn
            color = Color.DarkGray, // Màu xám đậm
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally) // Căn giữa ngang
        )

        // Trường nhập tên
        OutlinedTextField(
            value = name, // Giá trị hiện tại của tên
            onValueChange = { name = it }, // Cập nhật giá trị khi người dùng nhập
            label = { Text("name") }, // Nhãn hiển thị bên trong trường nhập
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng
        )

        // Trường nhập email
        OutlinedTextField(
            value = email, // Giá trị hiện tại của email
            onValueChange = { email = it }, // Cập nhật giá trị khi người dùng nhập
            label = { Text("Email") }, // Nhãn hiển thị bên trong trường nhập
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng
        )

        // Trường nhập mật khẩu
        OutlinedTextField(
            value = password, // Giá trị hiện tại của mật khẩu
            onValueChange = { password = it }, // Cập nhật giá trị khi người dùng nhập
            label = { Text("Password") }, // Nhãn hiển thị bên trong trường nhập
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(), // Chiếm toàn bộ chiều rộng
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Hiển thị hoặc ẩn mật khẩu
            trailingIcon = { // Biểu tượng hiển thị/ẩn mật khẩu
                IconButton(onClick = { passwordVisible = !passwordVisible }) { // Đổi trạng thái khi nhấn
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, // Chọn biểu tượng dựa trên trạng thái
                        contentDescription = "Toggle password visibility" // Mô tả trợ năng
                    )
                }
            }
        )

        // Trường nhập xác nhận mật khẩu
        OutlinedTextField(
            value = confirmPassword, // Giá trị hiện tại của xác nhận mật khẩu
            onValueChange = { confirmPassword = it }, // Cập nhật giá trị khi người dùng nhập
            label = { Text("Confirm Password") }, // Nhãn hiển thị bên trong trường nhập
            modifier = Modifier
                .padding(bottom = 24.dp)
                .fillMaxWidth(), // Chiếm toàn bộ chiều rộng
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(), // Hiển thị hoặc ẩn mật khẩu
            trailingIcon = { // Biểu tượng hiển thị/ẩn xác nhận mật khẩu
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) { // Đổi trạng thái khi nhấn
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, // Chọn biểu tượng dựa trên trạng thái
                        contentDescription = "Toggle confirm password visibility" // Mô tả trợ năng
                    )
                }
            }
        )


        // Nút "SIGN UP"
        Button(
            onClick = {
                navController.navigate(ROUTE_NAME.login.name)

            }, // Điều hướng đến màn hình Home khi nhấn
            modifier = Modifier
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng
                .height(50.dp), // Chiều cao 50dp
            colors = ButtonDefaults.buttonColors(Color.Black) // Màu nền đen
        ) {
            Text(
                text = "SIGN UP", // Nội dung văn bản
                color = Color.White, // Màu trắng
                fontSize = 20.sp // Cỡ chữ 20sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp)) // Khoảng trống 8dp giữa nút và văn bản

        // Nút văn bản để chuyển đến màn hình đăng nhập
        TextButton(
            onClick = { }, // Điều hướng đến màn hình Login khi nhấn
            modifier = Modifier.align(Alignment.CenterHorizontally) // Căn giữa ngang
        ) {
            Text(
                text = "Already have an account? SIGN IN", // Nội dung văn bản
                color = Color.Black // Màu đen
            )
        }
    }
}

// Hàm xem trước giao diện trong IDE
@Preview(showBackground = true, showSystemUi = true) // Hiển thị nền và giao diện hệ thống
@Composable
fun SignupPreview() {
    Assigment_KOT104_PH48770Theme  { // Áp dụng theme tùy chỉnh của ứng dụng
        SignupScreen(navController = rememberNavController()) // Hiển thị màn hình Signup với NavController giả lập
    }
}