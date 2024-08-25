package com.example.enocinterview.features.home.data.api

import com.example.enocinterview.features.home.data.model.GravatarProfile
import retrofit2.Response

class MockGravatarApiService : GravatarApiService {
    override suspend fun getProfile(profileIdentifier: String): Response<GravatarProfile> {
        return Response.success(
            GravatarProfile(
                hash = "31c5543c1734d25c7206f5fd591525d0295bec6fe84ff82f946a34fe970a1e66",
                display_name = "Alex Morgan",
                profile_url = "https://gravatar.com/example",
                avatar_url = "https://2.gravatar.com/avatar/ff1a7ffd57eb8ffc0868e24fc3dd84b9",
                avatar_alt_text = "Alex Morgan's avatar image. Alex is smiling and standing in beside a large dog who is looking up at Alex.",
                location = "New York, USA",
                description = "I like playing hide and seek.",
                job_title = "Landscape Architect",
                company = "ACME Corp",
                verified_accounts = emptyList(),
                pronunciation = "Al-ex Mor-gan",
                pronouns = "She/They",
                timezone = "Europe/Bratislava",
                languages = emptyList(),
                first_name = "Alex",
                last_name = "Morgan",
                is_organization = false,
                links = emptyList(),
                interests = emptyList(),
                payments = null,
                contact_info = null,
                gallery = emptyList(),
                number_verified_accounts = 3,
                last_profile_edit = "2021-10-01T12:00:00Z",
                registration_date = "2021-10-01T12:00:00Z",
                error = null
            )
        )
    }
}