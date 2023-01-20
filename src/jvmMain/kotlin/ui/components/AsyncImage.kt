package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.unit.Density
import consts.Colors
import data.cli.Ktor
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import kotlin.io.use


@Composable
fun AsyncImageBitmap(
    url: String,
    modifier: Modifier,
    contentDisposition: String? = null,
    shape: Shape = RectangleShape,
) {

    var image: ImageBitmap? by rememberSaveable {
        mutableStateOf(null)
    }

    val imageBtmp: ImageBitmap? by produceState(image) {
        if (value == null) {
            try {
                value = loadBitmap(url)
            } catch (t: Throwable) {
                val errorText = t.message ?: t.localizedMessage
                println("AsyncImage Error - $errorText")
            }
        }
    }

    if (imageBtmp == null) {
        Box(
            modifier.background(
                Colors.gray,
                shape
            )
        )
    }

    AnimatedVisibility(
        visible = imageBtmp != null,
        enter = fadeIn(animationSpec = tween(750)),
        exit = fadeOut(animationSpec = tween(750))
    ) {
        Image(
            bitmap = imageBtmp!!,
            contentDescription = contentDisposition,
            contentScale = ContentScale.Crop,
            modifier = modifier.clip(shape)
        )
    }
}

@Composable
fun AsyncImageXmlVector(
    url: String,
    modifier: Modifier,
    contentDisposition: String? = null,
    shape: Shape = RectangleShape,
) {

    val density = LocalDensity.current
    val image: Painter? by rememberSaveable {
        mutableStateOf(null)
    }

    val imageXml: Painter? by produceState(image) {
        if (value == null) {
            try {
                value = loadSvg(url, density)
            } catch (t: Throwable) {
                val errorText = t.message ?: t.localizedMessage
                println("AsyncImage Error - $errorText")
            }
        }
    }

    if (imageXml == null) {
        Box(
            modifier.background(
                Colors.gray,
                shape
            )
        )
    }

    AnimatedVisibility(
        visible = imageXml != null,
        enter = fadeIn(animationSpec = tween(750)),
        exit = fadeOut(animationSpec = tween(750))
    ) {

    }

    if (imageXml !=  null) {
        Image(
            painter = imageXml!!,
            contentDescription = contentDisposition,
            modifier = modifier.clip(shape)
        )
    }
}

suspend fun loadSvg(url: String, density: Density): Painter =
    urlStream(url).use {
        loadSvgPainter(it, density)
    }

suspend fun loadBitmap(url: String):ImageBitmap = HttpClient(CIO) {
    install(WebSockets)
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
    defaultRequest {
        header("Authorization", "Basic ${Ktor.token}")
    }
}.use {
    withContext(Dispatchers.IO) {
        val response = it.get(url)
        ByteArrayInputStream(response.readBytes())
    }
}
    .use (::loadImageBitmap)
    .also {
        it.prepareToDraw()
        return it
    }


private suspend fun urlStream(url: String) = HttpClient(CIO).use {
    ByteArrayInputStream(it.get(url).readBytes())
}




