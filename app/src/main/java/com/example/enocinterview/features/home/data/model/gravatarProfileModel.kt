package com.example.enocinterview.features.home.data.model


data class GravatarProfile(
    val hash: String,
    val display_name: String,
    val profile_url: String,
    val avatar_url: String,
    val avatar_alt_text: String?,
    val location: String?,
    val description: String?,
    val job_title: String?,
    val company: String?,
    val verified_accounts: List<VerifiedAccount>?,
    val pronunciation: String?,
    val pronouns: String?,
    val timezone: String?,
    val languages: List<String>?,
    val first_name: String?,
    val last_name: String?,
    val is_organization: Boolean,
    val links: List<Link>?,
    val interests: List<Interest>?,
    val payments: Payments?,
    val contact_info: ContactInfo?,
    val gallery: List<Gallery>?,
    val number_verified_accounts: Int?,
    val last_profile_edit: String?,
    val registration_date: String?,
    val error: String?
)

data class VerifiedAccount(
    val service_type: String,
    val service_label: String,
    val service_icon: String,
    val url: String
)

data class Link(
    val label: String,
    val url: String
)

data class Interest(
    val id: Int,
    val name: String
)

data class Payments(
    val links: List<Link>?,
    val crypto_wallets: List<CryptoWallet>?
)

data class CryptoWallet(
    val label: String,
    val address: String
)

data class ContactInfo(
    val home_phone: String?,
    val work_phone: String?,
    val cell_phone: String?,
    val email: String?,
    val contact_form: String?,
    val calendar: String?
)

data class Gallery(
    val url: String,
    val alt_text: String
)

