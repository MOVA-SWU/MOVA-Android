package com.example.mova.data.model.response

object GenreMapper {
    private val genreMap = mapOf(
        28 to "액션",
        12 to "모험",
        16 to "애니메이션",
        35 to "코미디",
        80 to "범죄",
        99 to "다큐멘터리",
        18 to "드라마",
        10751 to "가족",
        14 to "판타지",
        36 to "역사",
        27 to "공포",
        10402 to "음악",
        9648 to "미스터리",
        10749 to "로맨스",
        878 to "SF",
        10770 to "TV 영화",
        53 to "스릴러",
        10752 to "전쟁",
        37 to "서부"
    )

    fun getGenreMap(): Map<Int, String> {
        return genreMap
    }
}