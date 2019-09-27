package org.sportsstories.domain.repository

import kotlinx.coroutines.Deferred
import org.joda.time.DateTime
import org.sportsstories.domain.model.Game
import org.sportsstories.domain.model.GameStatus
import org.sportsstories.domain.model.TeamShort
import org.sportsstories.utils.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepository @Inject constructor() {

    fun getGamesAsync(): Deferred<List<Game>> = async {
        mockGames()
    }

    // TODO REMOVE MOCK
    private fun mockGames() = listOf(
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Испания"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Испания"),
                    GameStatus.LIVE,
                    DateTime.now(),
                    8,
                    0
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Россия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Англия"),
                    GameStatus.ENDED,
                    DateTime.now(),
                    2,
                    1
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Россия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Германия"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Украина"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Франция"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Бельгия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Франция"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Бельгия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Германия"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Испаsния"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Испwания"),
                    GameStatus.LIVE,
                    DateTime.now(),
                    8,
                    0
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Ро23ссия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Ан4глия"),
                    GameStatus.ENDED,
                    DateTime.now(),
                    2,
                    1
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Россия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Герма5ния"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Ук5раина"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Франция"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Бельгия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Фран2ция"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            ),
            Game(
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/04/25/82/58/rue00d7d50127.jpeg", "Бельгия"),
                    TeamShort( "https://s5o.ru/storage/simple/ru/edt/1f/d8/0a/0f/rue5eb1e45e52.png", "Германи2я"),
                    GameStatus.NOT_STARTED,
                    DateTime.now()
            )
    )

}
