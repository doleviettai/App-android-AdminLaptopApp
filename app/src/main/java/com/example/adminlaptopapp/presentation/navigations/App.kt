package com.example.adminlaptopapp.presentation.navigations

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ProductionQuantityLimits
import androidx.compose.material.icons.outlined.Reorder
import androidx.compose.material.icons.outlined.Slideshow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.adminlaptopapp.R
import com.example.adminlaptopapp.presentation.StartAppScreen
import com.example.adminlaptopapp.presentation.screens.banner.AllBannerScreenUi
import com.example.adminlaptopapp.presentation.screens.category.AllCategoryScreenUi
import com.example.adminlaptopapp.presentation.screens.order.AllOrderScreenUi
import com.example.adminlaptopapp.presentation.screens.product.AllProductScreenUi
import com.example.adminlaptopapp.presentation.screens.user.AllUserScreenUi
import com.example.adminlaptopapp.presentation.screens.HomeScreenUi
import com.example.adminlaptopapp.presentation.screens.banner.AddBannerScreenUi
import com.example.adminlaptopapp.presentation.screens.banner.GetBannerByNameScreenUi
import com.example.adminlaptopapp.presentation.screens.banner.UpdateBannerScreenUi
import com.example.adminlaptopapp.presentation.screens.category.AddCategoryScreenUi
import com.example.adminlaptopapp.presentation.screens.category.GetCategoryByNameScreenUi
import com.example.adminlaptopapp.presentation.screens.category.UpdateCategoryScreenUi
import com.example.adminlaptopapp.presentation.screens.order.GetOrderByCodeScreenUi
import com.example.adminlaptopapp.presentation.screens.order.UpdateOrderScreenUi
import com.example.adminlaptopapp.presentation.screens.product.AddProductScreenUi
import com.example.adminlaptopapp.presentation.screens.product.GetProductByIdScreenUi
import com.example.adminlaptopapp.presentation.screens.product.UpdateProductScreenUi
import com.example.adminlaptopapp.presentation.screens.user.ExportUserPdfByEmailScreenUi
import com.example.adminlaptopapp.presentation.screens.user.GetUserByEmailScreenUi
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.google.firebase.auth.FirebaseAuth


data class BottomNavItem(
    val name:String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector,
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(
    firebaseAuth: FirebaseAuth
) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    // Chỉ show bottomBar nếu không phải StartAppScreen
    val shouldShowBottomBar = currentDestination != SubNavigation.StartAppScreen::class.qualifiedName

    val bottomNavItems = listOf(
        BottomNavItem(name = "Home", icon = Icons.Default.Home, unselectedIcon = Icons.Outlined.Home),
        BottomNavItem(name = "Prod", icon = Icons.Default.ProductionQuantityLimits, unselectedIcon = Icons.Outlined.ProductionQuantityLimits),
        BottomNavItem(name = "Cate", icon = Icons.Default.Category, unselectedIcon = Icons.Outlined.Category),
        BottomNavItem(name = "Banner", icon = Icons.Default.Slideshow, unselectedIcon = Icons.Outlined.Slideshow),
        BottomNavItem(name = "User", icon = Icons.Default.Person, unselectedIcon = Icons.Outlined.Person),
        BottomNavItem(name = "Order", icon = Icons.Default.Reorder, unselectedIcon = Icons.Outlined.Reorder),
    )

    val startScreen = SubNavigation.StartAppScreen

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars
                                .asPaddingValues()
                                .calculateBottomPadding()
                        ),
                ) {
                    AnimatedBottomBar(
                        selectedItem = selectedItem,
                        itemSize = bottomNavItems.size,
                        containerColor = Color.Transparent,
                        indicatorColor = colorResource(id = R.color.orange),
                        indicatorDirection = IndicatorDirection.BOTTOM,
                        indicatorStyle = IndicatorStyle.FILLED
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            bottomNavItems.forEachIndexed { index, navigationItem ->
                                BottomBarItem(
                                    selected = selectedItem == index,
                                    onClick = {
                                        selectedItem = index
                                        when (index) {
                                            0 -> navController.navigate(Routes.HomeScreen)
                                            1 -> navController.navigate(Routes.GetAllProductScreen)
                                            2 -> navController.navigate(Routes.GetAllCategoryScreen)
                                            3 -> navController.navigate(Routes.GetAllBannerScreen)
                                            4 -> navController.navigate(Routes.GetAllUserScreen)
                                            5 -> navController.navigate(Routes.GetAllOrderScreen)
                                        }
                                    },
                                    imageVector = navigationItem.icon,
                                    label = navigationItem.name,
                                    containerColor = Color.Transparent,
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = if (shouldShowBottomBar) 60.dp else 0.dp
                )
        ) {
            NavHost(
                navController = navController,
                startDestination = startScreen
            ) {
                composable<SubNavigation.StartAppScreen> {
                    StartAppScreen(navController = navController)
                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUi(navController = navController)
                    }

                    composable<Routes.GetAllProductScreen> {
                        AllProductScreenUi(navController = navController)
                    }

                    composable<Routes.GetAllCategoryScreen> {
                        AllCategoryScreenUi(navController = navController)
                    }

                    composable<Routes.GetAllBannerScreen> {
                        AllBannerScreenUi(navController = navController)
                    }

                    composable<Routes.GetAllUserScreen> {
                        AllUserScreenUi(navController = navController)
                    }

                    composable<Routes.GetAllOrderScreen> {
                        AllOrderScreenUi(navController = navController)
                    }
                }

                composable<Routes.AddProductScreen> {
                    AddProductScreenUi(navController = navController)
                }

                composable<Routes.GetProductByIdScreen> {
                    val productId : Routes.GetProductByIdScreen = it.toRoute()
                    GetProductByIdScreenUi(navController = navController, productId = productId.productId)
                }

                composable<Routes.UpdateProductScreen> {
                    val productId : Routes.GetProductByIdScreen = it.toRoute()
                    UpdateProductScreenUi(navController = navController, productId = productId.productId)
                }

                composable<Routes.AddCategoryScreen>{
                    AddCategoryScreenUi(navController = navController)
                }

                composable<Routes.GetCategoryByNameScreen> {
                    val categoryName : Routes.GetCategoryByNameScreen = it.toRoute()
                    GetCategoryByNameScreenUi(navController = navController, productName = categoryName.categoryName)
                }

                composable<Routes.UpdateCategoryScreen> {
                    val categoryName : Routes.GetCategoryByNameScreen = it.toRoute()
                    UpdateCategoryScreenUi(navController = navController, productName = categoryName.categoryName)
                }

                composable<Routes.AddBannerScreen>{
                    AddBannerScreenUi(navController = navController)
                }

                composable<Routes.GetBannerByNameScreen> {
                    val bannerName : Routes.GetBannerByNameScreen = it.toRoute()
                    GetBannerByNameScreenUi(navController = navController, productName = bannerName.bannerName)
                }

                composable<Routes.UpdateBannerScreen> {
                    val bannerName : Routes.GetBannerByNameScreen = it.toRoute()
                    UpdateBannerScreenUi(navController = navController, productName = bannerName.bannerName)
                }

                composable<Routes.GetUserByEmailScreen> {
                    val userEmail : Routes.GetUserByEmailScreen = it.toRoute()
                    GetUserByEmailScreenUi(navController = navController, email = userEmail.userEmail)
                }

                composable<Routes.ExportUserPdfByEmailScreen> {
                    val userEmail : Routes.ExportUserPdfByEmailScreen = it.toRoute()
                    ExportUserPdfByEmailScreenUi(navController = navController, email = userEmail.userEmail)
                }

                composable<Routes.GetOrderByCodeScreen> {
                    val postalCode : Routes.GetOrderByCodeScreen = it.toRoute()
                    GetOrderByCodeScreenUi(navController = navController, postalCode = postalCode.postalCode)
                }

                composable<Routes.UpdateOrderScreen> {
                    val postalCode : Routes.GetOrderByCodeScreen = it.toRoute()
                    UpdateOrderScreenUi(navController = navController, postalCode = postalCode.postalCode)
                }

            }
        }
    }
}
