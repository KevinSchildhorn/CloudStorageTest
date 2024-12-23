package co.touchlab.kermittest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val mainScope = MainScope() // lifecycleScope is preferred if available.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainScope.launch(Dispatchers.IO) {
            //quickstart()
            //authenticateImplicitWithAdc()

        }

        setContent {
            val scope = rememberCoroutineScope()
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}