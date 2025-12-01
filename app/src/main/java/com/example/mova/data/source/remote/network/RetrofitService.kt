package com.example.mova.data.source.remote.network

import com.example.mova.data.model.request.DonationCompleteRequest
import com.example.mova.data.model.request.EmailCheckRequest
import com.example.mova.data.model.request.LogInRequest
import com.example.mova.data.model.request.MovieWriteRequest
import com.example.mova.data.model.request.NicknameRequest
import com.example.mova.data.model.request.RefreshTokenRequest
import com.example.mova.data.model.request.SignUpRequest
import com.example.mova.data.model.response.CharacterCollectResponse
import com.example.mova.data.model.response.CompanyDetailResponse
import com.example.mova.data.model.response.CompanyListResponse
import com.example.mova.data.model.response.EmailCheckResponse
import com.example.mova.data.model.response.LogInResponse
import com.example.mova.data.model.response.MissionDetailResponse
import com.example.mova.data.model.response.MissionListResponse
import com.example.mova.data.model.response.MovieDetailResponse
import com.example.mova.data.model.response.MovieListResponse
import com.example.mova.data.model.response.MovieWriteResponse
import com.example.mova.data.model.response.PointSumResponse
import com.example.mova.data.model.response.ProfileResponse
import com.example.mova.data.model.response.RefreshTokenResponse
import com.example.mova.data.model.response.SignUpResponse
import com.example.mova.data.model.response.VerificationResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @POST("/users/signup")
    suspend fun postSignUp(
        @Body signUpRequest: SignUpRequest
    ): SignUpResponse

    @POST("/users/emailCheck")
    suspend fun postEmailCheck(
        @Body emailCheckRequest: EmailCheckRequest
    ): EmailCheckResponse

    @POST("/users/login")
    suspend fun postLogin(
        @Body loginRequest: LogInRequest
    ): LogInResponse

    @POST("/auth/refresh")
    suspend fun postRefreshToken(
        @Body request: RefreshTokenRequest
    ): RefreshTokenResponse

    @POST("/users/logout")
    suspend fun postLogout(): Response<Unit>

    @GET("/home")
    suspend fun getMovieList(): List<MovieListResponse>

    @GET("/home/latest")
    suspend fun getLatestMovie(): List<MovieListResponse>

    @POST("/home/movies")
    suspend fun postMovieWrite(
        @Body movieWriteRequest: MovieWriteRequest
    ): MovieWriteResponse

    @GET("/home/movie-records/{movieRecordId}")
    suspend fun getMovieDetail(
        @Path("movieRecordId") movieId: Int
    ): MovieDetailResponse

    @GET("/movie-records/{movieRecordId}/missions")
    suspend fun getMissionDetail(
        @Path("movieRecordId") movieId: Int
    ): MissionDetailResponse

    @Multipart
    @POST("verifications/movie-records/{movieRecordId}")
    suspend fun patchMissionComplete(
        @Path("movieRecordId") movieRecordId: Int,
        @Part image: MultipartBody.Part
    ): Response<VerificationResponse>

    @GET("/myMissions")
    suspend fun getMissionAvailable(
        @Query("status") status: String
    ): List<MissionListResponse>

    @GET("/myMissions")
    suspend fun getMissionComplete(
        @Query("status") status: String
    ): List<MissionListResponse>

    @GET("/pointSum")
    suspend fun getPointSum(): PointSumResponse

    @GET("/companies")
    suspend fun getCompanyList(): List<CompanyListResponse>

    @GET("/companies/{companyId}")
    suspend fun getCompanyDetail(
        @Path("companyId") companyId: Int
    ): CompanyDetailResponse

    @PUT("/companies/{companyId}/sponsor")
    suspend fun putDonationComplete(
        @Path("companyId") companyId: Int,
        @Body status: DonationCompleteRequest
    ): Response<Unit>

    @GET("/myPage")
    suspend fun getProfile(): ProfileResponse

    @PATCH("/myPage/nickname")
    suspend fun patchNickname(
        @Body nicknameRequest: NicknameRequest
    ): Response<Unit>

    @GET("/myPage/collection-status")
    suspend fun getCharacterCollect(): CharacterCollectResponse
}