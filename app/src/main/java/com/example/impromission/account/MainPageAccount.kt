package com.example.impromission.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.impromission.R
import com.example.impromission.ui.theme.AppTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainPageAccount : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    AccountNavigationHost()
                }
            }
        }
    }
}

@Composable
fun AccountNavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "signIn" // Стартовый экран - авторизация
    ) {
        // Экран авторизации
        composable("signIn") {
            SignInScreen(
                onSignInClick = { /* Обработка успешного входа */
                    navController.navigate("profile")
                },
                onSignUpClick = {
                    navController.navigate("signUp")
                }
            )
        }

        // Экран регистрации
        composable("signUp") {
            SignUpScreen(
                onSignUpClick = { /* Обработка успешной регистрации */
                    navController.navigate("profile")
                },
                onSignInClick = {
                    navController.popBackStack() // Возврат к экрану входа
                }
            )
        }

        // Экран профиля (после авторизации)
        composable("profile") {
            ProfileScreen(
                onLogout = {
                    // Возврат к экрану авторизации с очисткой стека
                    navController.navigate("signIn") {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "User Profile",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AccountNavigationHostFilledPreview() {
    AppTheme {
        AccountNavigationHost()
    }
}