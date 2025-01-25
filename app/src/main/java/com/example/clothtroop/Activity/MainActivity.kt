package com.example.clothtroop.Activity

import android.graphics.Paint.Align
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.MotionLayoutDebugFlags
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.clothtroop.Model.CategoryModel
import com.example.clothtroop.Model.ItemsModel
import com.example.clothtroop.Model.ListItems
import com.example.clothtroop.Model.SliderModel
import com.example.clothtroop.R
import com.example.clothtroop.ViewModel.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityScreen()
        }
    }
}

@Composable
//@Preview
fun MainActivityScreen(){
    val viewModel=MainViewModel()

    val banners= remember { mutableStateListOf<SliderModel>() }
    val categories= remember { mutableStateListOf<CategoryModel>() }
    val popular= remember { mutableStateListOf<ItemsModel>()}

    var showBannerLoading by remember { mutableStateOf(true) }
    var showCategoryLoading by remember { mutableStateOf(true) }
    var showPopularLoading by remember { mutableStateOf(true) }

    //banner
    LaunchedEffect(Unit) {
        viewModel.loadBanner().observeForever{
            banners.clear()
            banners.addAll(it)
            showBannerLoading=false
        }
    }

    //category
    LaunchedEffect(Unit) {
        viewModel.loadCategory().observeForever{
            categories.clear()
            categories.addAll(it)
            showCategoryLoading=false
        }
    }

    //popular
    LaunchedEffect(Unit) {
        viewModel.loadPopular().observeForever{
            popular.clear()
            popular.addAll(it)
            showPopularLoading=false
        }
    }

    ConstraintLayout(modifier = Modifier.background(Color.White)){
        val(scrollList, bottomMenu)=createRefs()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(scrollList){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        ){
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column{
                        Text("Welcome Back", color = Color.Black)
                        Text("Jackie",
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row {
                        Image(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            painter = painterResource(R.drawable.bell_icon),
                            contentDescription = null
                        )
                    }
                }
            }

            // Banners
            item{
                if(showBannerLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }else{
                    Banners(banners)
                }
            }

            item{
                Text(
                    text = "Official Banner",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp)
                )
            }

            //Categories
            item{
                if(showCategoryLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }else{
                    CategoryList(categories)
                }
            }

            item{
                SectionTitle("Most Popular", "See All")
            }

            item{
                if(showPopularLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }else{
                    ListItems(popular)
                }
            }
        }
    }
}

@Composable
fun CategoryList(categories: SnapshotStateList<CategoryModel>) {
    var selectedIndex by remember { mutableStateOf(-1) }
    val context= LocalContext.current

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp)
    ){
        items(categories.size) { index->
            CategoryItem(
                item = categories[index],
                isSelected = selectedIndex==index,
                onItemClick = {
                    selectedIndex=index
                    Handler(Looper.getMainLooper()).postDelayed({

                    }, 500)
                }
            )
        }
    }
}

@Composable
fun CategoryItem(item:CategoryModel, isSelected:Boolean, onItemClick:()->Unit){
    Column(
        modifier = Modifier
            .clickable(onClick = onItemClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            model = (item.picUrl),
            contentDescription = item.title,
            modifier = Modifier
                .size(if(isSelected) 60.dp else 50.dp)
                .background(
                    color = if(isSelected) colorResource(R.color.darkBrown) else colorResource(R.color.lightBrown),
                    shape = RoundedCornerShape(100.dp)
                ),
            contentScale = ContentScale.Inside,
            colorFilter = if(isSelected) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Black)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = item.title,
            color = colorResource(R.color.darkBrown),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SectionTitle(title: String, actionText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = actionText,
            color = colorResource(R.color.darkBrown)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Banners(banners: List<SliderModel>) {
    AutoSlidingCarousel(banners = banners)
}

@Composable
//@Preview
fun AutoSlidingCarousel(
    modifier: Modifier=Modifier.padding(top = 16.dp),
    pagerState: PagerState= remember { PagerState() },
    banners: List<SliderModel>
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

//    val testUrl = "https://res.cloudinary.com/db97pgxv4/image/upload/v1737625844/banner0_crochz.png"
    Column(modifier = modifier.fillMaxSize()){
        HorizontalPager(count = banners.size, state = pagerState) { page ->
//            Log.d("Banners", "Image URL: ${banners[page].url}")
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(banners[page].url)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .height(150.dp)
            )
        }
        DotIndicator(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            totalDots = banners.size,
            selectedIndex = if(isDragged) pagerState.currentPage else pagerState.currentPage,
            dotSize = 8.dp
        )
    }
}

@Composable
fun DotIndicator(
    modifier: Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color= colorResource(R.color.darkBrown),
    unselectedColor: Color= colorResource(R.color.grey),
    dotSize: Dp
){
    LazyRow(
        modifier = modifier
            .wrapContentSize()
    ){
        items(totalDots){ index ->
            IndicatorDot(
                color = if(index==selectedIndex) selectedColor else unselectedColor,
                size = dotSize
            )
            if(index!=totalDots-1){
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier=Modifier,
    size: Dp,
    color: Color
){
    Box(
        modifier=modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}
