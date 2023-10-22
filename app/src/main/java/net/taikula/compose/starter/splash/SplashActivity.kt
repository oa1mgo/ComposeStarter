package net.taikula.compose.starter.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock.sleep
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.taikula.compose.starter.MainActivity
import net.taikula.compose.starter.R
import net.taikula.compose.starter.base.BaseComponentActivity
import net.taikula.compose.starter.ui.theme.ComposeStaterTheme
import kotlin.math.ceil

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ComposeStaterTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorScheme.background)
                ) {
                    var hasFinish by remember { mutableStateOf(false) }
                    CountdownText(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp),
                        initialTime = 5_000L,
                    ) {
                        if (hasFinish) {
                            return@CountdownText
                        }
                        hasFinish = true
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = ""
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun CountdownText(
        modifier: Modifier = Modifier,
        initialTime: Long = 1000,
        onFinish: () -> Unit = {},
    ) {
        val remainMillis = remember { mutableStateOf(initialTime) }
        LaunchedEffect(remainMillis.value) {
            withContext(Dispatchers.IO) {
                while (remainMillis.value > 0) {
                    sleep(1000)
                    withContext(Dispatchers.Main) {
                        remainMillis.value -= 1000
                    }
                }
            }
            onFinish()
        }

        Button(
            onClick = { onFinish() },
            modifier = modifier,
        ) {
            Text(
                text = getString(R.string.count_down_jump_text, ceil(remainMillis.value / 1000.0).toInt()),
                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                textAlign = TextAlign.Center,
            )
        }

    }
}

