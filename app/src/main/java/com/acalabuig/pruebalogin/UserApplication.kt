package com.acalabuig.pruebalogin

import android.app.Application
import androidx.room.Room
import com.acalabuig.pruebalogin.UserApplication.Companion.database
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserApplication: Application() {
    companion object {
        lateinit var database: UserDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "UserDatabase")
            .fallbackToDestructiveMigration()
            .build()

        loadNews()
    }


}

@OptIn(DelicateCoroutinesApi::class)
private fun loadNews() {

    GlobalScope.launch(Dispatchers.IO) {
        val noticiasObtenidas = database
            .noticiaDao()
            .getAllNoticias()
        if (noticiasObtenidas.size <= 0) {
            listOf(
                NoticiaEntity(
                    title = "El Real Madrid se proclama campeón de LaLiga tras una temporada dominante",
                    content = "El equipo dirigido por Carlo Ancelotti ha asegurado el título de LaLiga con varias jornadas de antelación.",
                    fecha = "2025-05-15",
                    esFavorita = false,
                    imageurl = "https://as.com/futbol/imagenes/2017/05/21/reportajes/1495372750_507161_1513078715_noticiareportajes_grande.jpg",
                    noticiaurl = "https://as.com/futbol/2017/05/21/reportajes/1495372750_507161.html"
                ),
                NoticiaEntity(
                    title = "Carlos Alcaraz conquista Roland Garros y se afianza en la cima del tenis mundial",
                    content = "El tenista español venció en la final a Novak Djokovic en un partido épico.",
                    fecha = "2025-06-10",
                    esFavorita = false,
                    imageurl = "https://img.asmedia.epimg.net/resizer/v2/J2LOSG3EDS6PGE3IAZRSRNAZUU.jpg?auth=119596c772a6dbbf80e94c4c7e3a1594070b54109deb1b9d4d926dc1ae262373&width=976&height=549&focal=1179%2C763",
                    noticiaurl = "https://as.com/tenis/alcaraz-doma-al-viento-y-a-los-canonazos-de-cilic-n/"
                ),
                NoticiaEntity(
                    title = "España se clasifica para la final de la Eurocopa tras vencer a Francia",
                    content = "La selección española logra un triunfo histórico en la semifinal con un gol en el último minuto.",
                    fecha = "2025-07-05",
                    esFavorita = false,
                    imageurl = "https://media.eitb.eus/multimedia/images/2024/07/09/3248323/20240709225030_lamineyamalespainiafrantz_foto610x342.jpg",
                    noticiaurl = "https://www.eitb.eus/es/deportes/futbol/detalle/9539026/espana-elimina-a-francia-21-y-se-clasifica-para-final-de-eurocopa/"
                ),
                NoticiaEntity(
                    title = "Max Verstappen gana el Gran Premio de Mónaco con una actuación impecable",
                    content = "El piloto neerlandés de Red Bull dominó la carrera de principio a fin en el mítico circuito urbano.",
                    fecha = "2025-05-26",
                    esFavorita = false,
                    imageurl = "https://phantom-marca-mx.unidadeditorial.es/fefbf271acdb3c936e2a24fc2e7db889/resize/660/f/webp/mx/assets/multimedia/imagenes/2023/05/28/16852867819178.jpg",
                    noticiaurl = "https://www.marca.com/mx/motor/formula-1/2023/05/28/64736d3a268e3ed2398b45a0.html"
                ),
                NoticiaEntity(
                    title = "El FC Barcelona presenta su nuevo estadio tras tres años de remodelación",
                    content = "El Spotify Camp Nou reabre con capacidad para 105.000 espectadores y tecnología de última generación.",
                    fecha = "2025-09-01",
                    esFavorita = false,
                    imageurl = "https://s3.sportstatics.com/relevo/www/multimedia/202409/26/media/cortadas/obras-camp-nou-buena-RY5UMp2YPRrCAnj9PKZJ0qO-1200x648@Relevo.jpg?w=569&h=320",
                    noticiaurl = "https://www.relevo.com/futbol/liga-primera/obras-camp-cuesta-cambia-estrena-20240926161403-nt.html"
                ),
                NoticiaEntity(
                    title = "Los Juegos Olímpicos de París 2024 cierran con una ceremonia espectacular",
                    content = "La delegación española ha conseguido 25 medallas, su mejor resultado en la historia de los JJOO.",
                    fecha = "2024-08-11",
                    esFavorita = false,
                    imageurl = "https://fotografias.lasexta.com/clipping/cmsimages02/2024/08/11/3511540C-CF1E-4BED-AE1D-805855975620/tom-cruise-bandera-olimpica_98.jpg?crop=1200,675,x0,y80&width=1900&height=1069&optimize=low&format=webply",
                    noticiaurl = "https://www.lasexta.com/noticias/deportes/otros-deportes/ceremonia-clausura-juegos-olimpicos-paris-2024-directo_2024081166b8fd1776ed0d0001356301.html"
                ),
                NoticiaEntity(
                    title = "Mbappé firma con el Real Madrid y se convierte en el fichaje del año",
                    content = "El delantero francés llega al club blanco tras varios años de rumores y especulaciones.",
                    fecha = "2025-07-01",
                    esFavorita = false,
                    imageurl = "https://img.asmedia.epimg.net/resizer/v2/HK4EQ2SSQZDULL3OWLIJ2U6F2M.jpg?auth=d58a7a1290ba82be60ce0f5e0918623a2156d805459d113db47abdc6de338403&width=976&height=549&focal=1681%2C812",
                    noticiaurl = "https://as.com/futbol/primera/mbappe-ficha-por-el-real-madrid-n/"
                ),
                NoticiaEntity(
                    title = "El Tour de Francia 2025 arranca con una contrarreloj espectacular en Bilbao",
                    content = "La ronda gala comienza en territorio español con un recorrido desafiante para los ciclistas.",
                    fecha = "2025-07-01",
                    esFavorita = false,
                    imageurl = "https://www.bizkaia.eus/documents/880303/0/VUELtA2025.jpg/ee8fb3af-8110-0565-fd85-927ed4119374?t=1734678400029",
                    noticiaurl = "https://www.bizkaia.eus/es/kirolbidepro/evento-detalle/-/asset_publisher/hAfe1q5ipej5/content/la-vuelta-2025-bilbao-bizkaia/880303"
                ),
                NoticiaEntity(
                    title = "Los Golden State Warriors ganan las Finales de la NBA tras una serie épica",
                    content = "Stephen Curry lideró al equipo californiano hacia su octavo anillo de campeón.",
                    fecha = "2025-06-20",
                    esFavorita = false,
                    imageurl = "https://spain.id.nba.com/storage/images/wallpapers/1655379973.jpg",
                    noticiaurl = "https://spain.id.nba.com/noticias/playoffs-2022"
                ),
                NoticiaEntity(
                    title = "Leo Messi anuncia su retirada del fútbol profesional",
                    content = "El astro argentino cuelga las botas tras una carrera llena de éxitos y títulos.",
                    fecha = "2025-12-20",
                    esFavorita = false,
                    imageurl = "https://files.antena2.com/antena2/public/styles/imagen_despliegue/public/downloaded_images/000-334p8ak.jpg.webp?VersionId=YPkDocA1OXRVVmxTqCLbHaxDYwUH09a.&itok=J4qQ3O3y",
                    noticiaurl = "https://www.antena2.com/futbol/messi-confirmo-su-fecha-de-retiro-se-despedira-con-la-camiseta-de-la-seleccion-argentina"
                )
            ).forEach { noticia ->
                database
                    .noticiaDao()
                    .insert(noticia)
            }
        }
    }
}