package pt.iade.ei.firstapp



import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.firstapp.activities.Conex
import pt.iade.ei.firstapp.ui.theme.FirstAppTheme
import pt.iade.ei.firstapp.ui.theme.SocialPoseTheme
//import pt.iade.ei.firstapp.samples.generateLorem
//import pt.iade.ei.firstapp.samples.generateUrlImage
import kotlin.random.Random


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(navController: NavController) {

    val pageState = rememberPagerState(pageCount = { 20 })

    VerticalPager(
        state = pageState,
        modifier = Modifier.fillMaxSize()
    ) { page ->

        var isLiked by rememberSaveable { mutableStateOf(false) }
        val title = rememberSaveable { generateLorem(Random.nextInt(2, 10)) }
        val likes = rememberSaveable { Random.nextInt(2, 1000).toString() }
        val imageUrl = rememberSaveable { generateUrlImage() }

        PostItem(
            imageUrl = imageUrl,
            title = title,
            likes = likes,
            isLiked = isLiked,
            onLikeClick = { isLiked = !isLiked },
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    SocialPoseTheme {
        val navController = rememberNavController()
        FeedScreen(navController = navController)
    }
}